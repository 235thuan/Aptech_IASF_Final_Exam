package org.example.iasf_final_exam.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.iasf_final_exam.model.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.name = :username", User.class);
        query.setParameter("username", username);

        return query.getResultStream().findFirst().orElse(null);
    }
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> searchByName(String name) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}