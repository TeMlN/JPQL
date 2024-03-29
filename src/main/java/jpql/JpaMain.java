package jpql;

import javax.persistence.*;
import java.util.Collection;
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
            /*
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
                    */


            System.out.println("======= Out JOIN =======");
            /*
            Team team2 = new Team();
            team2.setName("teamA");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setAge(10);

            member.setTeam(team2);

            em.persist(member2);

            String query2 = "select m from Member m left join Team t on m.username = t.name";
            List<Member> resultList2 = em.createQuery(query2, Member.class)
                    .getResultList();

            System.out.println("resultList2 = " + resultList2.size());
            */

            /*
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', true From Member m " +
                            "where m.type = jpql.MemberType.ADMIN";
            List<Object[]> result = em.createQuery(query).getResultList();


            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }*/


            /*Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', true From Member m " +
                    "where m.type = :userType";

            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }*/

            System.out.println("===== JPQL CASE 식 =====");
            /*
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select " +
                                "case when m.age <= 10 then '학생요금' " +
                                "     when m.age >= 60 then '경로요금' " +
                                "     else '일반요금' end " +
                           "from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }
            */

            System.out.println("====== COALESCE ======");
            /*
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername(null);
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select coalesce(m.username, '이름없는 회원') as username from Member m "; //coalesce = 하나씩 조회해서 null이 아니면 반환
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }
*/

            System.out.println("====== NULLIF ======");
            /*
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select nullif(m.username, '관리자') as username from Member m "; //nullif = 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }
            */
/*
            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);
            
            em.flush();
            em.clear();

            String query = "select t.members From Team t";
            List<Collection> resultList = em.createQuery(query, Collection.class)
                    .getResultList();

            System.out.println("resultList = " + resultList);*/


            /*Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select distinct t From Team t join fetch t.members"; // 지연로딩 보다 fetch join이 우선순위가 높다.
            //1:N 조인은 값이 뻥튀기가 될수도 있다 (중복 출력)

            List<Team> result = em.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("result = " + result.size());
            for (Team team : result) {
                System.out.println("team = " + team.getName() + " " + team.getMembers().size());
                for(Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }
*/
/*
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select t From Team t"; // fetch 조인에는 별칭을 주면 안된다 (alias)
            //1:N 조인은 값이 뻥튀기가 될수도 있다 (중복 출력)

            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("result = " + result.size());
            for (Team team : result) {
                System.out.println("team = " + team.getName() + " " + team.getMembers().size());
                for(Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }*/

/*
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m where m.team = :team";

            List<Member> members = em.createQuery(query, Member.class)
                    .setParameter("team", teamA)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member = " + member);
            }*/
/*
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            //Named 쿼리의 장점 : 애플리케이션 로딩 시점에 초기화를 하여 사용하기 때문에 오타가 나더라도 문제가 없음 (컴파일 쪽에서 잡아주기 때문)
            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member);
            }*/


            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate(); // 영향을 받은 엔티티 수를 반환 해주는 method (현재 경우 모든 멤버가 age가 20으로 변경되었으므로 멤버의 갯수 3개이다.)

            System.out.println("resultCount = " + resultCount);

            em.clear(); // 벌크 연산후 영속성 컨텍스트 초기화 해주기 (쿼리를 날려도 db에만 반영될 뿐 영속성 컨텍스트는 그대로 이기 떄문에)

            Member member = em.find(Member.class, member1.getId());

            System.out.println("member = " + member.getAge());

            System.out.println("member1 = " + member1.getAge());
            System.out.println("member2 = " + member2.getAge());
            System.out.println("member3 = " + member3.getAge());

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
