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
    public <T> T save(T entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <T> T update(T entity) {
        return em.merge(entity);
    }

    @Transactional
    public CursoAlunoEntity assinaCurso(CursoAlunoEntity entity){
        em.persist(entity);
        return entity;
    }

    public List<CursoEntity> listAllCourses() {
        return em.createQuery("from CursoEntity", CursoEntity.class).getResultList();
    }

    public CursoEntity searchCursoById(Long cursoId) {
        List<CursoEntity> resultados = em.createQuery(
            "FROM CursoEntity WHERE id = :cursoId", CursoEntity.class)
            .setParameter("cursoId", cursoId)
            .getResultList();

        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public AulaEntity searchAulaById(Long aulaId) {
        List<AulaEntity> resultados = em.createQuery(
            "FROM AulaEntity WHERE id = :aulaId", AulaEntity.class)
            .setParameter("aulaId", aulaId)
            .getResultList();

        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public List<AulaEntity> searchAulaByCurso(Long curso_id){
        List<AulaEntity> resultados = em.createQuery(
            "FROM AulaEntity WHERE curso_id = :curso_id", AulaEntity.class)
            .setParameter("curso_id", curso_id)
            .getResultList();

        return resultados.isEmpty() ? null : resultados;
    }


    public List<AulaEntity> listAllLessons() {
        return em.createQuery("from AulaEntity", AulaEntity.class).getResultList();
    }


}
