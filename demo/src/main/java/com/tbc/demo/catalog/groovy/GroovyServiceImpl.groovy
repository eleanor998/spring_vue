package com.tbc.demo.catalog.groovy

import com.tbc.demo.catalog.asynchronization.model.User

class GroovyServiceImpl implements GroovyService {

    User getUser(String name, String age) {
        def user = new User()
        user.setAge(age as Integer)
        user.setUserName(name)
        return user

    }
}
