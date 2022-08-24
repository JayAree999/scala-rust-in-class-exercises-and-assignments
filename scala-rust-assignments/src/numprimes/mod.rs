

use rayon::iter::*;

#[allow(dead_code)]
pub fn par_is_prime(n: u64) -> bool {

    if n <= 1 {
        return false 
    }
    let sqrtN = (n as f64).sqrt() as u64;
    
    let twotosqrtN : Vec<u64> = (2..=sqrtN).collect();
    
    let checkisPrime = twotosqrtN.into_par_iter().filter((|x|{n%x==0})).count();

    if checkisPrime > 0 {
        return false
    } else {
        return true
    }

    



}


#[allow(dead_code)]
pub fn par_count_primes(n: u32) -> usize {
    let numlst: Vec<u32> = (2..=n).collect();
    return numlst.into_par_iter().filter(|num| par_is_prime(*num as u64)==true).count();
}

#[cfg(test)]
mod tests {
    use crate::numprimes::{par_count_primes, par_is_prime};

    #[test]
    fn basic_is_prime() {
        assert_eq!(false, par_is_prime(0));
        assert_eq!(false, par_is_prime(1));
        assert_eq!(true, par_is_prime(2));
        assert_eq!(true, par_is_prime(3));
        assert_eq!(false, par_is_prime(6));
        assert_eq!(false, par_is_prime(25));
        assert_eq!(true, par_is_prime(41));
    }
    #[test]
    fn basic_count_primes() {
        assert_eq!(25, par_count_primes(100));
        assert_eq!(78498, par_count_primes(1_000_000));
    }
}
