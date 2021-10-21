package jpql;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString // 사실 지금 예제에서는 toString을 어노테이션으로 설정 하면 안된다. 그 이유는 어노테이션으로 할시 team까지 toString까지 포함하기 때문에 순환참조가 일어날 수 있습니다.
public class Member {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private String username;
    private int age;

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }


}
