# NOResolang
Esolang with only NOR operation

An esoteric programming language where the only operation you can use is NOR along with pointer shifting. Like BF, except with booleans instead of bytes. Since NOR (and NAND) is a universal gate, you can make any other logic gate out of NORs.

- - -

Given an infinite line of 0 bits and a single hold bit, you can use these operations:

* '<' shift pointer left
* '>' shift pointer right
* '^' set pointer value to the NOR of the hold value with the pointer
* 'v' set hold value to the NOR of the hold value with the pointer
* '@' loop if pointer nor hold
* '#' end loop
* ',' take a char input (and NOR each bit to the left of pointer with the char's binary representation)
* '.' output a char (from the preceeding bits of a char)

- - -

Exmples:

`^>^>>>>.`

"nor 0 with 0 to get 1, go one right, do the same, move right 4" - gives you 110000, which is 0 via ASCII

`^>^>>>>.^.^<^>.^.^<^<^>>.^.^<^>.^.^<^<^<^>>>.^.`

0123456789

`,^<^<^<^<^<^<^>>>>>>.`

prints input

`,^<^<^<^<^<^>>>>>.^@^.^#`

(Turing) input 1: 111...; input 0: 0; otherwise: undefined behavior
