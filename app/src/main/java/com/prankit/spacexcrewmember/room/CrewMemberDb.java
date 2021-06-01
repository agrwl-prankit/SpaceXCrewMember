package com.prankit.spacexcrewmember.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CrewMemberRoomModel.class}, version = 1)
public abstract class CrewMemberDb extends RoomDatabase {

    public abstract CrewMemberDAO crewMemberDAO();

}
