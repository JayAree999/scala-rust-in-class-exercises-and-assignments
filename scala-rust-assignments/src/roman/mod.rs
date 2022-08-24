#[allow(dead_code)]

static LOOKUP: [(u16, &'static str); 13] = [(1000, "M"), (900, "CM"), (500, "D"), (400, "CD"),
(100, "C"), (90, "XC"), (50, "L"), (40, "XL"),
(10, "X"), (9, "IX"), (5, "V"), (4, "IV"), (1, "I")];

pub fn to_roman(n: u16) -> String {

    let mut n1 = n;
    let mut i = 0;
    let mut buf = String::new();
    while n1 > 0 {
        if n1 >= LOOKUP[i].0 {
            buf += LOOKUP[i].1;
            n1 -= LOOKUP[i].0;
        } else {
            i += 1;
        }
    }

    return buf;
}

fn roman_char_to_arabic_digit(roman: char) -> usize {

    let mut i = 0;


    for &(n, s) in &LOOKUP {
        if i % 2 == 0 && s.chars().nth(0) == roman.to_uppercase().next() {
            return n.into();
        }
        i+=1;
    }

    0
}
#[allow(dead_code)]
pub fn parse_roman(roman_number: &str) -> u16 {
    
    let iter = roman_number
              .chars()
              .map(roman_char_to_arabic_digit);

    let mut num = 0;
    let mut prev = 0;

    for d in iter {


        num += d;

        if prev != 0 && d > prev {
            num -= prev * 2;
        }

        prev = d;
    }

    num as u16
}

#[cfg(test)]
mod tests {
    use super::{parse_roman, to_roman};

    #[test]
    fn basic_digits() {
        assert_eq!("I", to_roman(1));
        assert_eq!("V", to_roman(5));
        assert_eq!("X", to_roman(10));
        assert_eq!("L", to_roman(50));
        assert_eq!("C", to_roman(100));
    }

    #[test]
    fn basic_mixture() {
        assert_eq!("II", to_roman(2));
        assert_eq!("IV", to_roman(4));
        assert_eq!("IX", to_roman(9));
        assert_eq!("XII", to_roman(12));
        assert_eq!("XIV", to_roman(14));
        assert_eq!("MCMLIV", to_roman(1954));
    }

    #[test]
    fn basic_parsing() {
        assert_eq!(3, parse_roman("III"));
        assert_eq!(4, parse_roman("IV"));
        assert_eq!(8, parse_roman("VIII"));
        assert_eq!(19, parse_roman("XIX"));
    }
}
