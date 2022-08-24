use bitvec::macros::internal::funty::Fundamental;
use rayon::{
    iter::{IntoParallelIterator, ParallelBridge, ParallelIterator},
    str::ParallelString,
};
use std::{fs, io};
struct FlightRecord {
    unique_carrier: String,
    actual_elapsed_time: i32,
    arrival_delay: i32,
}
pub fn par_split<'a>(st_buf: &'a str, split_char: char) -> Vec<&'a str> {
    //place flags use plus scan like the previous problem
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

    let mut s = st_buf
        .par_chars()
        .into_par_iter()
        .map(|i| if i == split_char { 1 } else { 0 })
        .collect::<Vec<i32>>();

    let (vec_scans, c_sum) = plus_scan(s);

    unsafe {
        let mut output = Vec::with_capacity(c_sum.as_usize());
        let mut j: usize = 0;

        for i in 1..vec_scans.len() - 1 {
            if vec_scans[i] < vec_scans[i + 1] {
                let k = &st_buf[j..i];
                output.push(k);
                j = i + 1;
            }
        }
        output.push(&st_buf[j..vec_scans.len()]);

        return output;
    }
}

#[allow(dead_code)]
fn parse_line(line: &str) -> Option<FlightRecord> {
    let vec_str: Vec<&str> = par_split(line, ',');

    if vec_str[8].is_empty() {
        return None;
    }

    if vec_str[11] == "NA" {
        vec_str[11] == "0";
    }

    if vec_str[14] == "NA" {
        vec_str[14] == "0";
    }

    let flightRecord = FlightRecord {
        unique_carrier: String::from(vec_str[8]),
        actual_elapsed_time: i32::from(vec_str[11].parse::<i32>().unwrap()),
        arrival_delay: i32::from(vec_str[14].parse::<i32>().unwrap()),
    };
    return Some(flightRecord);
}

#[allow(dead_code)]
pub fn ontime_rank(filename: &str) -> Result<Vec<(String, f64)>, io::Error> {
    use chashmap::CHashMap;
    use rayon::prelude::*;
    //https://stackoverflow.com/questions/30801031/read-a-file-and-get-an-array-of-strings
    let file_contents = fs::read_to_string(filename)?;

    let lines = par_split(&file_contents, '\n');

    let data: Vec<Option<FlightRecord>> =
        lines.par_iter().map(|line| parse_line(line)).collect();

    let checkFlightTime = CHashMap::new();

    println!("reading file");
    data.par_iter().for_each(|col| {
        let has_item = if let Some(_value) = col { true } else { false };
        if (has_item) {
            let FlightRecord {unique_carrier,actual_elapsed_time,arrival_delay} = col.as_ref().unwrap();

            if *arrival_delay <= 0 {
                //https://docs.rs/chashmap/2.2.0/chashmap/struct.CHashMap.html
                checkFlightTime.upsert(unique_carrier,
                    || (1, 1),|(k, v)| {*k += 1;*v += 1;},
                );
            } else {
                checkFlightTime.upsert(unique_carrier, || (0, 1), |(_, v)| *v += 1);
            }
        }
        
    checkFlightTime.remove(&String::from("UniqueCarrier"));
    });
    println!("CALCULATE PERCENTAGE");

    let mut percentage: Vec<(String, f64)> = checkFlightTime
        .into_iter()
        .map(|(k, (o, c))| (k.clone(), o as f64 / c as f64))
        .collect();
    println!("SORTING");
    percentage.par_sort_unstable_by(|(_, p), (_, p1)| p1.partial_cmp(p).unwrap());

    // todo!("Write me")

    Ok(percentage)
}
