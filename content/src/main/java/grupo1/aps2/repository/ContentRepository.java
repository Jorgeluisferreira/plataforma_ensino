package grupo1.aps2.repository;

import java.util.List;

import grupo1.aps2.model.CourseEntity;
import grupo1.aps2.model.LessonEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ContentRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public <T> void save(T entity) {
        em.persist(entity);
    }

    public List<CourseEntity> listAllCourses() {
        return em.createQuery("from CourseEntity", CourseEntity.class).getResultList();
    }

    public List<LessonEntity> listAllLessons() {
        return em.createQuery("from LessonEntity", LessonEntity.class).getResultList();
    }
}
