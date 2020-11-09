package com.baoanh.JobSteaming

class Person(var firstName: String,
             var lastName: String,
             var sex: String,
             var age: Long) {
    def getFirstName(): String = {
        return firstName
    }

    def setFirstName(firstName: String): Unit = {
        this.firstName = firstName
    }

    def getLastName(): String = {
        return lastName
    }

    def setLastName(lastName: String): Unit = {
        this.lastName = lastName
    }

    def getSex(): String = {
        return sex
    }

    def setSex(sex: String): Unit = {
        this.sex = sex
    }

    def getAge(): Long = {
        return age
    }

    def setAge(age: Long): Unit = {
        this.age = age
    }
}
