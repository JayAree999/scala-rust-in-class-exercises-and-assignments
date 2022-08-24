use bitvec::vec;
#[allow(dead_code)]
use num_bigint::{BigUint, ToBigInt, ToBigUint};
use rayon::prelude::*;
pub fn par_fib_seq(n: u32) -> Vec<num_bigint::BigUint> {
    let mut vec: Vec<num_bigint::BigUint> = Vec::new();

    let A = (1, 1, 1, 0);
    if n == 0 {
        return vec![0.to_biguint().unwrap()];
    }

    if n == 1 {
        return vec![1.to_biguint().unwrap()];
    }

    if n == 2 {
        return vec![1.to_biguint().unwrap()];
    }


    let subA = vec![(1, 0, 0, 1)].into_par_iter();
    let expIter = subA.chain(rayon::iter::repeatn((1, 1, 1, 0), n as usize - 2));
    let multedIter = expIter.collect();

    let (mut ps, c_sum) = plus_scan( multedIter);

    ps.push(c_sum);
    
    let res: Vec<num_bigint::BigUint> = ps
        .par_iter_mut()
        .map(|x| (x.2 + x.3).to_biguint().unwrap())
        .collect::<Vec<num_bigint::BigUint>>();

    return res;

}
fn plus_scan(xs: Vec<(i32, i32, i32, i32)>) -> (Vec<(i32, i32, i32, i32)>, (i32, i32, i32, i32)) {
    //from lect20.pdf change + to multiply matrix
    use rayon::iter::*;
    let defaultA = (1, 1, 1, 0);
    if xs.is_empty() {
        return (vec![], defaultA);
    }
    let half = xs.len() / 2;
    let (c_prefix, mut c_sum) = plus_scan(
        (0..half)
            .into_par_iter()
            .map(|i| multiply(xs[2 * i], xs[2 * i + 1]))
            .collect::<Vec<(i32, i32, i32, i32)>>(),
    );
    let mut pfs: Vec<(i32, i32, i32, i32)> = (0..half)
        .into_par_iter()
        .flat_map(|i| vec![c_prefix[i], multiply(c_prefix[i], xs[2 * i])])
        .collect();
    if xs.len() % 2 == 1 {
        pfs.push(c_sum);
        c_sum = multiply(c_sum, xs[xs.len() - 1]); // change here to push sum res and multiply w/ xs.len-1
    }

    (pfs, c_sum)
}

fn multiply(a: (i32, i32, i32, i32), b: (i32, i32, i32, i32)) -> (i32, i32, i32, i32) {
    //https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
    let x = a.0 * b.0 + a.1 * b.2;
    let y = a.0 * b.1 + a.1 * b.3;
    let z = a.2 * b.0 + a.3 * b.2;
    let w = a.2 * b.1 + a.3 * b.3;

    (x, y, z, w)
}

/*
W(n) = W(n/2) + O(n)
     = O(n)
S(n) = S(n/2) + O(logn)
     = O(log^2n)
*/
