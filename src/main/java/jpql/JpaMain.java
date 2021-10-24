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

            System.out.println("======== 객체지향 쿼리 언어 기본 =========");
            /*
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
            */


            System.out.println("======== 프로젝션 =========");
            // SELECT m FROM Member m -> 엔티티 프로젝션 (멤버 entity 를 조회)
            // SELECT m.team FROM Member m -> 엔티티 프로젝션 (team 을 조회)
            // SELECT m.address FROM Member m -> 임베디드 타입 프로젝션 (address 를 조회)
            // SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션 (스칼라 타입 : 숫자, 문자등 기본 데이터 타입)


            System.out.println("========= em.createQuery().getResultList 로 만들어진 List는 영속성 컨텍스트가 관리하는지 테스트 ========");
            /*
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            Member findMember = result.get(0); //update 쿼리가 나간다면 영속성 컨텍스트에서 관리한다는 뜻

            findMember.setAge(20);
            */


            System.out.println("======= Object[] 타입으로 조회 =======");
            /*
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List resultList = em.createQuery("select m.username, m.age From Member m")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] result = (Object[]) o;

            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);
            */


            System.out.println("======= new 명령어로 조회 =======");
            /*

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<MemberDto> resultList = em.createQuery("select new jpql.MemberDto(m.username, m.age) From Member m", MemberDto.class)
                    .getResultList();

            MemberDto memberDto = resultList.get(0);
            System.out.println("memberDto.getUsername = " + memberDto.getUsername());
            System.out.println("memberDto.getAge() = " + memberDto.getAge());
            // 단순 값을 dto로 바로 조회
            // 패키지 명을 포함한 전체 클래스 명 입력
            // 순서와 타입이 일치하는 생성자 필요
            */

            System.out.println("============ paging(페이징) query ============");

            /*
            for(int i=0; i<100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0) // 이 code가 페이징의 핵심!
                    .setMaxResults(10) // 이 code가 페이징의 핵심!
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }
            */


            System.out.println("======== Inner JOIN ========");
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team1);

            em.persist(member);

            String query1 = "select m from Member m inner join m.team t";
            List<Member> resultList1 = em.createQuery(query1, Member.class)
                    .getResultList();


            System.out.println("======= Out JOIN =======");
            Team team2 = new Team();
            team2.setName("teamA");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setAge(10);

            member.setTeam(team2);

            em.persist(member);

            String query2 = "select m from Member m left join Team t on m.username = t.name";
            List<Member> resultList2 = em.createQuery(query2, Member.class)
                    .getResultList();

            System.out.println("resultList2 = " + resultList2.size());
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
