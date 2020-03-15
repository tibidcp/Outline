package com.tibi.geodesy.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProjectDao {
    @Query("select * from project where name = :name")
    fun getProject(name: String): Project?

    @Query("select * from project")
    fun getAllProjects(): LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProject(project: Project)
    @Update
    fun updateProject(project: Project)
}

@Database(entities = [Project::class], version = 1, exportSchema = false)
abstract class ProjectDatabase : RoomDatabase() {
    abstract val projectDao: ProjectDao
}

private lateinit var INSTANCE: ProjectDatabase

fun getProjectsDatabase(context: Context): ProjectDatabase {
    synchronized(ProjectDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ProjectDatabase::class.java,
                "projects").build()
        }
    }
    return INSTANCE
}