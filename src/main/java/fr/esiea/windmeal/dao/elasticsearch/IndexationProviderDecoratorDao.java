package fr.esiea.windmeal.dao.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.model.enumeration.Tags;
import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.ExecutionException;


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
public class IndexationProviderDecoratorDao implements ICrudDao<FoodProvider> {

    private static final String INDEX = "foodprovider";
    private static final Logger LOGGER = Logger.getLogger(IndexationProviderDecoratorDao.class);

    @Autowired
    ObjectMapper mapper;

    @Autowired
    @Qualifier("providerDao")
    private ICrudDao<FoodProvider> foodProviderCrudService;
    Client esClient;

    public IndexationProviderDecoratorDao(String address,int port, String cluster) {

        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", cluster).build();

        esClient= new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }

    @Override
    public Iterable<FoodProvider> getAll() throws DaoException {
        return foodProviderCrudService.getAll();
    }

    @Override
    public void remove(String id) throws DaoException {
        FoodProvider model = foodProviderCrudService.getOne(id);

        esClient.prepareDelete(INDEX, getTypes(model.getTags()), model.getId())
                .execute()
                .actionGet();

        foodProviderCrudService.remove(id);
    }


    @Override
    public void save(FoodProvider model) throws DaoException {

        IndexResponse response = esClient.prepareIndex(INDEX, getTypes(model.getTags()), model.getId())
                .setSource(getJson(model)
                )
                .execute()
                .actionGet();

        if(response.getVersion() == 1)
            LOGGER.error("You update a document that was not indexed by elastic search");

        foodProviderCrudService.save(model);
    }

    @Override
    public void insert(FoodProvider model) throws DaoException {

        model.generateId();

        IndexResponse response = esClient.prepareIndex(INDEX, getTypes(model.getTags()), model.getId())
                .setSource(getJson(model)
                )
                .execute()
                .actionGet();

        foodProviderCrudService.insert(model);
        if(response.getVersion() != 1)
            LOGGER.error("You insert a document that was already indexed by elastic search");
    }


    @Override
    public FoodProvider getOne(String id) throws DaoException {
        return foodProviderCrudService.getOne(id);
    }

    private String getJson(FoodProvider model) {
        try {
            //TODO reduce the information given to elasticsearch
            String json = mapper.writeValueAsString(model);
            LOGGER.info("Json for elastic search " + json);
            return json;
        } catch (JsonProcessingException e) {
            LOGGER.error("Json serialisation errors");
        }
        return null;
    }

    private String getTypes(Set<Tags> tags) {
        //TODO Should be possible to optimize
        StringBuilder builder = new StringBuilder();
        for(Tags tag: tags) {
            builder.append(tag.getTagName()+",");
        }
        builder.deleteCharAt(builder.length() - 1);
        LOGGER.info("Builder type for elastic search " + builder);
        return builder.toString();
    }
}
