package fr.esiea.windmeal.service.order;

import fr.esiea.windmeal.model.Order;
import fr.esiea.windmeal.model.deffered.OrderDeferredResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Map;

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
@Service
public class UpdateOrderService  {
    private static final Logger LOGGER = Logger.getLogger(UpdateOrderService.class);
    Map<String,LinkedBlockingQueue<Order>> foodProviders = new HashMap<String, LinkedBlockingQueue<Order>>();

    public void update(Order order) {
        String foodProviderId = order.getFoodProviderId();
        if(foodProviders.containsKey(foodProviderId)) {//let's add it to the queue
            try {
                foodProviders.get(foodProviderId).put(order);
            } catch (InterruptedException e) {
                //TODO make it service exception handle it
                throw new RuntimeException(e);
            }
        }else   {//Let's create the queue
            LinkedBlockingQueue queue = new LinkedBlockingQueue();
            try {
                queue.put(order);
            }catch (InterruptedException e) {
                //TODO make it service exception handle it
                throw new RuntimeException(e);
            }
            foodProviders.put(foodProviderId,queue);
        }
    }

    public void getUpdate(OrderDeferredResult result,String providerId) {
        if(foodProviders.containsKey(providerId))   {
            //TODO to remove
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            LinkedBlockingQueue<Order> queue = foodProviders.get(providerId);
            try {
                if(queue != null && !queue.isEmpty())   {
                    result.setResult(queue.take());

                    if(queue.size() == 0) {// Kepp map small as possible
                        foodProviders.remove(providerId);
                    }
                }
            } catch (InterruptedException e) {
                //TODO make it service exception handle it
                throw new RuntimeException(e);
            }
        }
    }

}
