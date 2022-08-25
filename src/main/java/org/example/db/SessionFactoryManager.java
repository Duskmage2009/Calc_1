package org.example.db;

import org.example.entity.CalculationsHistory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {

    private static SessionFactoryManager sessionFactoryManager;
    private SessionFactory sessionFactory;

    private SessionFactoryManager() {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(CalculationsHistory.class)
                .buildSessionFactory();

    }

    public static SessionFactoryManager getSessionFactoryManager() {
        if (sessionFactoryManager == null) {
            sessionFactoryManager = new SessionFactoryManager();
        }
        return sessionFactoryManager;
    }

    public SessionFactory getSession() {

        return sessionFactory;
    }
}
