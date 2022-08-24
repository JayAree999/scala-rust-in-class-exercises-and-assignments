mod cp;
mod fib;
mod filter;
mod ontime;
mod sudoku;

fn main() {
    println!("{:?}", fib::par_fib_seq(10));
}
