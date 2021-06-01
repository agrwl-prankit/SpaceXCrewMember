package com.prankit.spacexcrewmember.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrewMemberDAO {

    @Insert
    void insertCrew(CrewMemberRoomModel crewMemberRoomModel);

    @Query("DELETE FROM CrewMember")
    void deleteAllMember();

    @Query("SELECT * FROM CrewMember")
    List<CrewMemberRoomModel> getAllMember();
}
