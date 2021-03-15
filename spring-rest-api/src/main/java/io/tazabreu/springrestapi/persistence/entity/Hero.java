package io.tazabreu.springrestapi.persistence.entity;

import io.tazabreu.springrestapi.domain.enumerable.QuestStatus;
import io.tazabreu.springrestapi.domain.enumerable.Race;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public
class Hero {
    private @Id
    @GeneratedValue
    Long id;
    private String firstName;
    private String lastName;
    private String role;
    private @Enumerated(EnumType.STRING)
    Race race;
    private @Enumerated(EnumType.STRING)
    QuestStatus questStatus;

    private Hero() {
    }

    public Hero(String name, String role, Race race) {
        setName(name);
        this.role = role;
        this.race = race;
        this.questStatus = QuestStatus.NOT_ASSIGNED;
    }

    public String getName() {
        return this.lastName != null ? String.format("%s %s", this.firstName, this.lastName) : this.firstName;
    }

    public void setName(String fullName) {
        String[] firstAndLastName = fullName.split(" ");
        this.firstName = firstAndLastName[0];
        this.lastName = firstAndLastName.length > 1 ? firstAndLastName[1] : "";
    }
}