package fr.esiea.windmeal.service.search.elasticsearch.implementation;

import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.ICrudProviderDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.model.Menu;
import fr.esiea.windmeal.service.search.elasticsearch.ISearchProviderService;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Copyright (c) 2013 ESIEA M. Labusquiere D. Déïs
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class ElasticsearchProviderService implements ISearchProviderService {
    private static final String INDEX = "foodprovider";
    private static final Logger LOGGER = Logger.getLogger(ElasticsearchProviderService.class);
    /*
     * TODO
     * This class worked but the field should be automaticly selected (maybe a way to search on all fields
     * INDEX should be in a propertie file
     * Sheety code
     */

    @Autowired
    @Qualifier("providerDao")
    private ICrudProviderDao providerDao;

    @Autowired
    @Qualifier("menuDao")
    private ICrudDao<Menu> menuDao;

    Client esClient;
    private final String[] fields ={"description","name","tags"};

    public ElasticsearchProviderService(String address, int port, String cluster) {

        final Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", cluster).build();

        this.esClient= new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        esClient.close();
     }

    @Override
    public Iterable<FoodProvider> search(String searchingTerms)  {
        return search(INDEX,searchingTerms,this.fields);
    }

    private Iterable<FoodProvider> search(String index,String searchingTerms,String... field)  {
        SearchResponse response = esClient.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.multiMatchQuery(searchingTerms, field))
                .setFrom(0).setSize(20).setExplain(true)
                .execute()
                .actionGet();

        SearchHit[] docs = response.getHits().getHits();

        if(docs.length == 0)
            return Collections.EMPTY_LIST;//No Result

        ArrayList<FoodProvider> foodProviders = new ArrayList<FoodProvider>();
        for(SearchHit doc : docs)   {
                String id = doc.getId();
            try {
                FoodProvider provider = providerDao.getOne(id);
                foodProviders.add(provider);
            } catch (DaoException e) {
                String message = "there is a mismatching between items in the dao and elasticsearch for the id " + id;
                LOGGER.error(message);
                throw new RuntimeException(message);
            }
        }

        return foodProviders;
    }
}
