package grupo1.aps2.repository;

import java.util.List;

import grupo1.aps2.model.AulaEntity;
import grupo1.aps2.model.CursoAlunoEntity;
import grupo1.aps2.model.CursoEntity;
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

    public void assinaCurso(CursoAlunoEntity entity){
        em.persist(entity);
    }

    public List<CursoEntity> listAllCourses() {
        return em.createQuery("from CourseEntity", CursoEntity.class).getResultList();
    }

    public List<AulaEntity> listAllLessons() {
        return em.createQuery("from LessonEntity", AulaEntity.class).getResultList();
    }


}
