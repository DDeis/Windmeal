package fr.esiea.windmeal.test.integration.fill;

import au.com.bytecode.opencsv.CSVReader;
import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.model.Menu;
import fr.esiea.windmeal.model.User;
import fr.esiea.windmeal.model.security.Profile;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.activation.DataHandler;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.esiea.windmeal.test.integration.fill.helper.FillHelper.getUser;

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
public class UserImportation {

    public static void main(String[] args) throws IOException, DaoException, ServiceException {

        ICrudService<User> userService;

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/application-context.xml");
        //By using service u are sure than logic rules are applyed
        userService = (ICrudService<User>) applicationContext.getBean("userCrudService");
        ClassPathResource cpr = new ClassPathResource("data/users.csv");
        List<Map<String, String>> usersList = readContactCSV(cpr.getURL().getPath());

        User user;

        for(Map<String,String> userMap:usersList)  {
            user = new User();
            user = getUser(userMap.get("email"),userMap.get("password"),Profile.valueOf(userMap.get("profile")));
            userService.insert(user);
        }
    }

    public static List<Map<String, String>> readContactCSV(String url) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        String[] header;
        try {
            CSVReader reader = new CSVReader(new FileReader(url),';');
            //get the header
            if((header= reader.readNext()) == null)
                return null;//File empty
            System.out.println(header);
            String [] nextLine = new String[header.length];

            for(int i=0;(nextLine = reader.readNext()) != null;i++) {
                Map<String,String> map = new HashMap();

                for(int j=0;j<nextLine.length;j++) {
                    map.put(header[j],nextLine[j]);
                }
                list.add(map);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return list;
    }
}