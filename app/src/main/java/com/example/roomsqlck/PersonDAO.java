package com.example.roomsqlck;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PersonDAO {
    @Insert(onConflict = REPLACE)
void insertPerson(Person person);
    @Delete
void deletePerson(Person person);
    @Delete
void reset(List<Person> personList);
    @Query("UPDATE persons SET `person-name` = :name , `person-tuoi` = :tuoi WHERE id = :id")
void update(int id,String name,int tuoi);
    @Query("SELECT * FROM persons")
List<Person> getAll();
}
