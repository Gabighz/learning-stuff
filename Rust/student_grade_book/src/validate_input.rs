/// Module with functions that validate the various types of input that the user could provide.

/// Validates user input by checking if it is within the range of the options
/// presented by the first CLI menu
///
/// # Arguments
///
/// * `user_input' - A string literal that should be parsed 
///                  as an integer from 1 to 6 inclusive
///
/// # Example
///
/// ```
/// let valid_option = validate_first_input('1'.to_string()); // true
/// let invalid_option = validate_first_input('0'.to_string()); // false
/// ```
pub fn validate_first_input (user_input: String) -> bool {

    let mut validated = false;

    match user_input.parse::<u8>() {
        // Accept as valid input only integers in the range 1 to 6 inclusive
        Ok(1..=6) => {
            validated = true;
        }
        // matches with anything that is not an integer or
        // an integer that is not within the range of 1 to =6
        _ => println!("Please enter a number between 1 and 6"),
    
    };

    return validated;

}

/// In the context of the user providing the ID of a student,
/// checks if the input is an unsigned integer of 8 bytes.
///
/// # Arguments
///
/// * `user_input' - A string literal that should be parsed 
///                  as an integer of size 8 bytes
///
/// # Example
///
/// ```
/// let valid_id = validate_name_input('1'.to_string()); // true
/// let invalid_id = validate_id_input('a'.to_string()); // false
/// ```
pub fn validate_id_input (user_input: String) -> bool {

    let mut validated = false;

    match user_input.parse::<u8>() {
        // Accept as valid input only integers that have a size of 8 bytes
        Ok(_u8) => {
            validated = true;
        }
        // matches with anything that is not an integer or
        // an integer that is bigger than 8 bytes
        _ => println!("Invalid input!"),
    
    };

    return validated;

}

/// In the context of the user providing the name of a student,
/// checks if the input is an alphabetic string (from a-z and A-Z only).
///
/// # Arguments
///
/// * `user_input' - A string literal that should be completely
///                  alphabetic
///
/// # Example
///
/// ```
/// let valid_name = validate_name_input('a'.to_string()); // true
/// let invalid_name = validate_name_input('1'.to_string()); // false
/// ```
pub fn validate_name_input(name: String) -> bool {

    let mut validated = false;

    // NOTE: .chars.count() is O(N). If perfomance is a constraint, 
    // and you only expect simple ASCII strings as input, maybe use
    // .chars.len() which is O(1). Also note that len() returns
    // the length in bytes.
    if name.chars().all(char::is_alphabetic) == true && name.chars().count() < 50 {
        validated = true;
    } else {
        println!("Not a valid name!");
    }

    return validated;

}

/// In the context of the user providing the date of birth of a student,
/// checks if the input is a reasonable date of birth
///
/// # Arguments
///
/// * `user_input' - A string literal that should be in
///                  the dd.mm.yyyy format
///
/// # Example
///
/// ```
/// validate_dob_input("10.05.1990"); // true
/// validate_dob_input("asd"); // false
/// ```
pub fn validate_dob_input (user_input: String) -> bool {

    // Assuming the user correctly entered the date in a
    // dd.mm.yyyy format, we attempt to split it
    let mut split_input = user_input.split(".").collect::<Vec<_>>();

    if split_input.len() == 3 {

        // if the day is between 1 and 31 inclusive, the month between 1 and 12 inclusive, and
        // the year between 1900 and 2020 inclusive, then we can consider the input as valid
        match split_input[0].parse::<u8>() {
            Ok(1..=31) => {}
            _ => {
                println!("Invalid day!");
                return false
            },
        
        };

        match split_input[1].parse::<u8>() {
            Ok(1..=12) => {}
            _ => {
                println!("Invalid month!");
                return false
            },
        
        };

        match split_input[2].parse::<u16>() {
            Ok(1900..=2020) => {
                // if the pattern matches above do not return false,
                // and this is ok as well,
                // then we can say that the input is validated
                return true;
            }
            _ => {
                println!("Invalid year!");
                return false
            },
        
        };
    } else {
        return false;
    }

}

#[cfg(test)]
mod tests {
    // importing names from outer (for mod tests) scope.
    use super::*;

    // tests the validate_first_input function
    #[test]
    fn validate_first_input_within_range() {
        assert_eq!(validate_first_input("1".to_string()), true);
        assert_eq!(validate_first_input("3".to_string()), true);
        assert_eq!(validate_first_input("6".to_string()), true);
    }

    // tests the validate_first_input function
    #[test]
    fn validate_first_input_outside_range() {
        assert_eq!(validate_first_input("-3".to_string()),false);
        assert_eq!(validate_first_input("0".to_string()), false);
        assert_eq!(validate_first_input("7".to_string()), false);
    }

    // tests the validate_first_input function
    #[test]
    fn validate_first_input_non_numeric(){
        assert_eq!(validate_first_input("asd".to_string()), false);
    }

    #[test]
    //tests the validate_name_input function
    fn validate_name_input_alphabetic() {
        assert_eq!(validate_name_input("asd".to_string()), true);
        assert_eq!(validate_name_input("ASD".to_string()), true);
        assert_eq!(validate_name_input("Stephen".to_string()), true);
    }

    #[test]
    //tests the validate_name_input function
    fn validate_name_input_numeric() {
        assert_eq!(validate_name_input("123".to_string()), false);
        assert_eq!(validate_name_input("A1S2D3".to_string()), false);
        assert_eq!(validate_name_input("aaa222".to_string()), false);
    }

    #[test]
    //tests the validate_name_input function
    fn validate_name_input_within_range() {
        assert_eq!(validate_name_input("a".repeat(49)), true);
    }

    #[test]
    //tests the validate_name_input function
    fn validate_name_input_outside_range() {
        assert_eq!(validate_name_input("a".repeat(50)), false);
    }

    #[test]
    //tests the validate_id_input function
    fn validate_id_input_numeric() {
        assert_eq!(validate_id_input("1".to_string()), true);
    }

    #[test]
    //tests the validate_id_input function
    fn validate_id_input_alphabetic() {
        assert_eq!(validate_id_input("asd".to_string()), false);
        assert_eq!(validate_id_input("a1s2d".to_string()), false);
        assert_eq!(validate_id_input("123asd".to_string()), false);
    }

    #[test]
    //tests the validate_id_input function
    fn validate_id_input_within_range() {
        assert_eq!(validate_id_input("0".to_string()), true);
        assert_eq!(validate_id_input("255".to_string()), true);
    }

    #[test]
    //tests the validate_id_input function
    fn validate_id_input_outside_range() {
        assert_eq!(validate_id_input("-1".to_string()), false);
        assert_eq!(validate_id_input("256".to_string()), false);
    }

    #[test]
    //tests the validate_dob_input function
    fn validate_dob_input_alphabetic() {
        assert_eq!(validate_dob_input("asd".to_string()), false);
        assert_eq!(validate_dob_input("256.asd.2000".to_string()), false);
        assert_eq!(validate_dob_input("1a.2d.2000".to_string()), false);
    }

    #[test]
    //tests the validate_dob_input function
    fn validate_dob_input_inside_range() {
        assert_eq!(validate_dob_input("12.10.1900".to_string()), true);
        assert_eq!(validate_dob_input("12.10.1990".to_string()), true);
        assert_eq!(validate_dob_input("01.05.2000".to_string()), true);
        assert_eq!(validate_dob_input("12.10.2020".to_string()), true);
    }

    #[test]
    //tests the validate_dob_input function
    fn validate_dob_input_outside_range() {
        assert_eq!(validate_dob_input("0.10.2000".to_string()), false);
        assert_eq!(validate_dob_input("32.10.2000".to_string()), false);
        assert_eq!(validate_dob_input("01.0.2000".to_string()), false);
        assert_eq!(validate_dob_input("01.13.2000".to_string()), false);
        assert_eq!(validate_dob_input("01.10.1899".to_string()), false);
        assert_eq!(validate_dob_input("01.10.2021".to_string()), false);
        assert_eq!(validate_dob_input("01.10.2021.20000".to_string()), false);
        assert_eq!(validate_dob_input("01.10".to_string()), false);
    }


}