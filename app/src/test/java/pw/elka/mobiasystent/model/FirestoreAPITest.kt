package pw.elka.mobiasystent.model

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class FirestoreAPITest{

    @Test
    fun getUserByEmailTest_existingEmail()
    {
        // arrange
        val existingEmail = "adam@mickiewicz.pl"
        // act
        val user = FirestoreAPI.getUserByEmail(existingEmail)
        Assert.assertNotNull(user)
    }

    @Test
    fun getUserByEmailTest_nonExistingEmail()
    {
        // arrange
        val nonExistingEmail = "gitarasciema@wp.pl"
        // act
        val user = FirestoreAPI.getUserByEmail(nonExistingEmail)
        Assert.assertNull(user)
    }
}