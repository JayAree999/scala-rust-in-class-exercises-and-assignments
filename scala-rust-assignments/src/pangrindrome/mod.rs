#[allow(dead_code)]
pub fn is_palindrome(s: &str) -> bool {

     if s.len() == 0 { return true }

    let s: Vec<char> = s.chars().collect();

    let mut first_idx = 0;

    let mut last_idx = s.len() - 1;

    while first_idx < last_idx {
     
        if !s[first_idx].is_alphabetic() { first_idx += 1; continue }
        if !s[last_idx].is_alphabetic() { last_idx -= 1; continue }


        if s[first_idx].to_ascii_lowercase() != s[last_idx].to_ascii_lowercase() {
            return false;
        }

        first_idx += 1;
        last_idx -= 1;
    }

    true
}

#[allow(dead_code)]
pub fn is_pangram(s: &str) -> bool {
  let s = s.to_lowercase();
  return ('a'..='z').all(|c| s.contains(c));
}


#[cfg(test)]
mod tests {
    use crate::pangrindrome::{is_palindrome, is_pangram};

    #[test]
    fn basic_is_palindrome() {
        assert_eq!(true, is_palindrome("r"));
        assert_eq!(true, is_palindrome("abba"));
        assert_eq!(true, is_palindrome("abcba"));
        assert_eq!(false, is_palindrome("abc"));
    }

    #[test]
    fn basic_pangram() {
        let quick_brown_fox = "The quick brown fox jumps over the lazy Dog";
        assert_eq!(true, is_pangram(quick_brown_fox));
        let quick_prairie_dog = "The quick prairie dog jumps over the lazy fox";
        assert_eq!(false, is_pangram(quick_prairie_dog));
    }
}
