use chashmap::CHashMap;
use rayon::iter::{IntoParallelRefIterator, ParallelIterator};
use std::collections::HashMap;


pub fn par_char_freq(chars: &[u8]) -> HashMap<u8, u32> {
    println!("{:?}",chars);

    chars.par_iter().map(|line|{
        let mut map = HashMap::<u8, u32>::new();
        
    
            (*map.entry(*line).or_insert(0)) += 1;
 
     
        map 
    }).reduce(|| HashMap::<u8, u32>::new() ,
              |mut a, b|{
                  for (k, v) in b {
                      *a.entry(k).or_insert(0) += v;
                  }
                  a
              })
}


#[cfg(test)]
mod tests {
    use crate::charfreq::par_char_freq;
    #[test]
    fn basic_charfreq() {
        println!("{:?}",par_char_freq("banana".as_bytes()));
    }
}
