package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            //반환 타입이 명확할때 : TypedQuery
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

            //반환 타입이 명확하지 않을 때 : Query
            Query query3 = em.createQuery("select m.username, m.age from Member m", Member.class);

            //Query 를 list 로 반환 **반환 할 결괏값이 많을때** ex)where age > 17
            List<Member> resultList = query1.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            //Query 를 SingleResult 로 반환 (즉 한개의 결괏값으로 반환) **값이 무조건 하나일 때만 사용**
            //결과가 없으면 : javax.persistence.NoResultException
            //결과가 둘 이상이면 : javax.persistence.NonUniqueResultException
            String singleResult1 = query2.getSingleResult();
            System.out.println("singleResult1 = " + singleResult1);


            //Parameter 바인딩 - 이름 기준
            Member result = em.createQuery("select m from Member m where m.username =:username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

            System.out.println("result = " + result.getUsername());
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
