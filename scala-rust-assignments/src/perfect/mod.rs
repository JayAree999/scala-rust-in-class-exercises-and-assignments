use std::{cmp::Ordering, ops::Range};

#[allow(dead_code)]
#[derive(Debug, Eq, PartialEq)] // make this enum type support equality test (i.e., ==)
pub enum Classification {
    Perfect,
    Deficient,
    Excessive,
}

fn get_factors_functional(n: u64) -> u64 {
    
    if n == 1 {
        return 0
    }
    (1..n-1).into_iter().filter(|&x| n % x == 0).sum()
}

#[allow(dead_code)]
pub fn classify_perfect(n: u64) -> Classification {
    
    let ans = get_factors_functional(n);

    if ans == n {
        return Classification::Perfect;
    } else if ans < n { 
        return Classification::Deficient;
    } else { 
        return Classification::Excessive;
    }

}

#[allow(dead_code)]
pub fn select_perfect(range: Range<u64>, kind: Classification) -> Vec<u64> {
    range.into_iter().filter(|&x| classify_perfect(x) == kind).collect::<Vec<u64>>()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn basic_classify() {
        use Classification::*;
        assert_eq!(classify_perfect(1), Deficient);
        assert_eq!(classify_perfect(6), Perfect);
        assert_eq!(classify_perfect(12), Excessive);
        assert_eq!(classify_perfect(28), Perfect);
    }

    #[test]
    fn basic_select() {
        use Classification::*;
        assert_eq!(select_perfect(1..10_000, Perfect), vec![6, 28, 496, 8128]);
        assert_eq!(
            select_perfect(1..50, Excessive),
            vec![12, 18, 20, 24, 30, 36, 40, 42, 48]
        );
        assert_eq!(
            select_perfect(1..11, Deficient),
            vec![1, 2, 3, 4, 5, 7, 8, 9, 10]
        );
    }
}
