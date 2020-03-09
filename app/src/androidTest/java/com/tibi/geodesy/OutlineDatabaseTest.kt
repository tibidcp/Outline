package com.tibi.geodesy

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tibi.geodesy.database.Measurement
import com.tibi.geodesy.database.OutlineDao
import com.tibi.geodesy.database.OutlineDatabase
import com.tibi.geodesy.database.Project
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class OutlineDatabaseTest {
    private lateinit var outlineDao: OutlineDao
    private lateinit var db: OutlineDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, OutlineDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        outlineDao = db.outlineDao
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertProject() {
        val project = Project(name = "Project1", instrument = "Nikon")
        val id = outlineDao.insertProject(project)
        assertEquals(id, 1L)
    }

    @Test
    @Throws(Exception::class)
    fun insertMeasurement() {
        assertEquals(outlineDao.insertMeasurement(Measurement(va = 0.0, ha = 0.0, sd = 0.0, ht = 0.0, projectName = "", stationId = 0, backsightId = 0)), 1)
    }
}