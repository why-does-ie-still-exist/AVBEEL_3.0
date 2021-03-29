# AVBEEL 3.0

The next iteration of **<ins>A</ins> <ins>V</ins>ery <ins>B</ins>ad <ins>E</ins>asily <ins>E</ins>xtensible <ins>L</ins>anguage**!

With new fetaures such as:
- Using metaprogramming so you don't have to implement a thousand interfaces to create a language feature
- Manageable Complexity!
- Control flow!
- Boolean logic!
- Recursion!
- Lists! (Maybe?)
- Slow runtimes! (*I'm using java reflection alright, you can't expect it to be that fast*)
- Documentation (*Coming Soon™, perpetually*)

### How to Run
*This project uses Java 11*

Build the project(It's entirely vanilla java, no dependencies) and run the REPL with:

`java -jar AVBEEL 3.0.jar`

Try an example that demonstrates mutial recursion!

`java -jar AVBEEL 3.0.jar [absolute path to mutualrecursion.txt in Examples folder]`

It should return:
```
false
true
1
21
```

`false` is the result from checking whether 73 is even,
`true` is the result from checking whether 73 is odd,
`1` is the second fibonacci number, and
`21` is the eighth fibonacci number.
