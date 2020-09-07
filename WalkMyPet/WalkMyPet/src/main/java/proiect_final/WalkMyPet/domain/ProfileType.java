package proiect_final.WalkMyPet.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name="profile_type")
public enum ProfileType {
    PROVIDER, PET_OWNER
}
