# TwoThreeTree
TwoThreeTree with function of range query


Author: Shuaicheng Zhang

Description: Using two three tree to achive range query
Invocation: java SearchTree {initial-hash-size} {block-size} {command-file}
The name of the program is SearchTree. Parameter {initial-hash-size} is the initial size of the hash table (in terms of slots). Parameter {block-size} is the initial size of the memory pool (in bytes). 

java SearchTree {initial-hash-size} {block-size} {command-file}
The name of the program is SearchTree. Parameter {initial-hash-size} is the initial size of the hash table (in terms of slots). Parameter {block-size} is the initial size of the memory pool (in bytes).  The program reads from text ﬁle {command-file} a series of commands, with one command per line. The program terminates after reading the end of the ﬁle. The formats for all commands are identical to Project 1. However, these new commands are added.

list {artist|song} {name}
If the ﬁrst parameter is artist then all songs by the artist with name {name} are listed. If the ﬁrst parameter is song then all artists who have recorded that song are listed. They are listed in the order that they appear in the 2-3+ tree.

delete {artist-name}<SEP>{song-name}
Delete the speciﬁc record for this particular song by this particular artist. This means removing two records from the 2-3+ tree, undoing the insert. If this is the last instance of that artist or of that song, then it needs to be removed from its hash table and the memory pool. In contrast, the remove command removes all instances of a given artist or song from the 2-3+ tree as well as the hash table and memory pool.

print tree
Print the strcuture of the tree
