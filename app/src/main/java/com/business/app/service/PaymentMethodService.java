package com.business.app.service;

import com.business.app.exception.NotFoundPaymentMethodException;
import com.business.app.exception.NotFoundUserException;
import com.business.app.model.PaymentMethod;
import com.business.app.repository.PaymentMethodRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService implements BeanNameAware, ApplicationContextAware {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod getPaymentMethod(Long id) throws NotFoundUserException {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElse(null);
        if (paymentMethod != null) {
            return paymentMethod;
        } else {
            throw new NotFoundPaymentMethodException("Способ платежа с таким id не найден!");
        }
    }


    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
