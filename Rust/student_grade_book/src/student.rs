/// Defines the 'Student' structure and methods for it

pub struct Student {
    id: u8,
    full_name: String,
    date_of_birth: [u16; 3],
}

impl Student {

    /// Returns a Student with the name and DoB given to them
    ///
    /// # Arguments
    ///
    /// * `name` - A string slice that holds the name of the person
    /// * `date_of_birth` - A fixed-sized array that holds three unsinged integers
    ///                     and represents the date of birth of the student
    ///
    /// # Example
    ///
    /// ```
    /// use crate::student::Student;
    /// let one_student = Student::new("name");
    /// ```
    pub fn new(id: u8, full_name: &str, date_of_birth: [u16; 3]) -> Student {
        Student {
            id: id,
            full_name: full_name.to_string(),
            date_of_birth: date_of_birth,
        }
    }
}