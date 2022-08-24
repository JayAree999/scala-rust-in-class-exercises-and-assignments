use rayon::iter::{IntoParallelRefIterator, ParallelIterator};

#[allow(dead_code)]


pub fn par_lin_search<T: Eq + Sync>(xs: &[T], k: &T) -> Option<usize> {

    if xs.len() <= 1 {
        return Some(0)
    }
  
    let (left, right) = xs.split_at(xs.len()/2);

    // make odd size vec avaiable/ prefer left
    fn lin_search<T: Eq + Sync>(xs: &[T], k: &T, lhs: usize ) -> Option<usize> {
        for (index, elt) in xs.iter().enumerate() {
        if *elt == *k {
        return Some(index+lhs);
        }
        }
        None
    }
    
    let (left_search, right_search) = rayon::join(|| lin_search(left,k, 0),
    || lin_search(right,k,left.len()));

    if left_search != None{
        return left_search;
    }
    else{
        return right_search ;
    }


}

/*(ii) As a comment block above your implementation, analyze the work and span of your implementation. 
If you write a recurrence, point out what it solves to. Explain your reasoning
 */

 /* W = O(n/2) + O(1) 
      = O(n)

 , S = 2W(n/2) + O(1) 
     = O(logn)  */
     

#[cfg(test)]
mod tests {
    use crate::linsearch::par_lin_search;

    #[test]
    fn basic_lin_search() {
        let xs = vec![3, 1, 4, 2, 7, 3, 1, 9];
        assert_eq!(par_lin_search(&xs, &3), Some(0));
        assert_eq!(par_lin_search(&xs, &5), None);
        assert_eq!(par_lin_search(&xs, &1), Some(1));
        assert_eq!(par_lin_search(&xs, &2), Some(3));
    }
}
