use bitvec::macros::internal::funty::Fundamental;
use rayon::iter::{IntoParallelIterator, ParallelIterator};
//use plus scan from lecture20
fn plus_scan(xs: Vec<i32>) -> (Vec<i32>, i32) {
    use rayon::iter::*;
    if xs.is_empty() {
        return (vec![], 0);
    }
    let half = xs.len() / 2;
    let (c_prefix, mut c_sum) = plus_scan(
        (0..half)
            .into_par_iter()
            .map(|i| xs[2 * i] + xs[2 * i + 1])
            .collect::<Vec<i32>>(),
    );

    let mut pfs: Vec<i32> = (0..half)
        .into_par_iter()
        .flat_map(|i| vec![c_prefix[i], c_prefix[i] + xs[2 * i]])
        .collect();
    if xs.len() % 2 == 1 {
        pfs.push(c_sum);
        c_sum += xs[xs.len() - 1]; 
    }
    (pfs, c_sum)
}
#[allow(dead_code)]
pub fn par_filter<F>(xs: &[i32], p: F) -> Vec<i32>
where
    F: Fn(i32) -> bool + Send + Sync,
{
    if xs.is_empty() {
        return Vec::new();
    }

    let mut vec_bools: Vec<i32> = xs
        .into_par_iter()
        .map(|x| if p(x.clone()) { 1 } else { 0 })
        .clone()
        .collect::<Vec<i32>>();

    let (mut vec_index, c_sum) = plus_scan(vec_bools);

    if (vec_index.len() % 2 != 0) {
        vec_index.push(c_sum);
    }
    let mut output = Vec::with_capacity(c_sum.as_usize());
    unsafe {
        for i in 0..vec_index.len() - 1 {
            if vec_index[i] < vec_index[i + 1] {
                output.push(xs[i])
            }
        }
        return output;
    }
}

fn isOdd(x: i32) -> bool {
    if x % 2 == 1 {
        return true;
    } else {
        false
    }
}

#[cfg(test)]
mod tests {
    use crate::filter::{isOdd, par_filter, plus_scan};
    use rayon::iter::*;

    #[test]
    fn my_test() {
        assert_eq!(vec![1, 3, 5], par_filter(&[1, 2, 3, 4, 5], isOdd));
    }
}
