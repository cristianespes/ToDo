package com.cristianespes.todo

import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.data.repository.TaskRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import java.util.*

class TaskRepositoryTest {

    // Si necesitamos probar el repository
    val repository: TaskRepository = mock()

    @Test
    fun `Repository should retrieve a list of tasks`() {
        //whenever or `when`
        whenever(repository.getAll()).then {
            Single.just(listOf(
                Task(1, "Whatever1", Date(), false, false),
                Task(2, "Whatever2", Date(), true,true)
            ))
        }


        repository.getAll()
            .test() // Clase para observar test
            .assertValue { it.size == 2 }
            .assertValue { it.first().id == 1L }
            .assertComplete()
    }

}