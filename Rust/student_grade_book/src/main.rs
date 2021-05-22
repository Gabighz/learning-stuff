/// Main program for manipulating student records, with a CLI

mod student;
mod validate_input;

use std::io::stdin;
use text_io::read;
use crate::validate_input::*;

fn main() {
    
    use crate::student::Student;

    println!("Hello, there!");

    println!("Choose one of the following options:");
    println!("1 - Add a student");
    println!("2 - Remove a student");
    println!("3 - Add grade to a student's record");
    println!("4 - Delete grade from student's record");
    println!("5 - Change grade from student's record");
    println!("6 - Exit");

    let mut user_input = String::new();
    let mut done = false;
    
    // while the user_input is not within range 1 to 6,
    // then ask for input
    while !done {

        user_input = String::new();

        stdin()
            .read_line(&mut user_input)
            .expect("failed to read from stdin");

        user_input = user_input.trim().to_string();
        done = validate_first_input(user_input.to_string());
    };

    // given that the user input has been validated
    // then act considering the option chosen
    act_on_option(user_input.parse::<u8>().unwrap());

}

/// Given the validated option chosen by the user,
/// do what the user has chosen.
///
/// # Arguments
///
/// * `validated_input' - An unsigned integer of 8 bytes that ranges
///                       from 1 to 6 inclusive
///
/// # Example
///
/// ```
/// act_on_option(1);
/// ```
fn act_on_option (validated_input: u8) {

    match validated_input {
        1 => {
            
            // If the function's argument is true,
            // then request full details (ID, name, DoB).
            // otherwise, request only the ID
            request_full_details(true);

        }
        // If somehow it isn't validated. For example, if the function is called
        // from someplace else than next to the first CLI menu
        _ => {
            println!("Incorrect option")
        }
    };

}

/// This function is called whenever the user chooses to manipulate student records,
/// it requests and validates partial or full details of the student.
/// The partial details consist of an ID. The full details consist of an ID, a name, 
/// and a date of birth
///
/// # Arguments
///
/// * full_details - A bool primitive
///
/// # Example
///
/// ```
/// request_full_details(true); // it's going to ask for ID, name, and DoB
/// request_full_details(false); // it's going to ask only for ID
/// ```
fn request_full_details(full_details: bool) {

    println!("Type the ID of the student");
    let mut id = String::new();

    let mut done_id = false;

    while !done_id {
        id = read!();

        done_id = validate_id_input(id.to_string());
    }

    if full_details == true {
        println!("Type the name of the student");
        let mut name = String::new();

        let mut done_name = false;

        while !done_name {
            name = read!();

            done_name = validate_name_input(name.to_string());
        };

        println!("Type the date of birth of the student in a dd.mm.yyyy format");
        let mut dob = String::new();

        let mut done_dob = false;

        while !done_dob {
            dob = read!();

            done_dob = validate_dob_input(dob.to_string());
        };
    }
}



