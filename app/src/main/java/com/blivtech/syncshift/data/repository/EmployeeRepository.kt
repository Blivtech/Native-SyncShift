package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.Employee

class EmployeeRepository {
    fun loadDummyEmployees(): MutableList<Employee> {

        val names = listOf(
            "Priya","Senathipathy","Sindhu","Saravanan","Kumar","Muthu","Ravi","Suresh",
            "Ganesh", "Meena","Radhika","Anil","Karthik","Deepa","Hari","Kavitha",
            "Vijay","Nataraj","Murali","Sasi"
        )

        val list = mutableListOf<Employee>()

        for (i in names.indices) {
            val gender = if (i % 2 == 0) "Female" else "Male"
            list.add(
                Employee(
                    id = i + 1,
                    name = names[i],
                    age = 20 + (i % 10),
                    city = "City ${i + 1}",
                    gender = gender
                )
            )
        }

        return list
    }
}