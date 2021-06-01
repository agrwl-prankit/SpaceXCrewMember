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

    @Delete
    void deleteAllMember(CrewMemberRoomModel crewMemberRoomModel);

    @Query("SELECT * FROM CrewMemberRoomModel")
    List<CrewMemberRoomModel> getAllMember();
}
