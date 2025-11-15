/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw1;

/**
 *
 * @author Panagiotis Tsatanis
 */
public class Trie {
    int TrieSize = 0;
    TrieNode Root;
    char[] Alphabet;
    
    public Trie () {
        this.Root = null;
        this.TrieSize = 0;
        Alphabet = new char[26];
        for(int i=0; i < Alphabet.length; i++) {
            Alphabet[i] = (char) ('a' + i);
        }
    }
    
    int getTrieSize() {
        return(this.TrieSize);
    }
    
    void IncreaseTrieSize() {
        this.TrieSize++;
    }
    
    void DecreaseTrieSize() {
        this.TrieSize--;
    }
    
    int min(int a, int b) {
        if( a <= b ) {
            return(a);
        }
        return(b);
    }
    
    int max(int a, int b) {
        if( a >= b ) { return(a); }
        return(b);
    }
    
    TrieNode getRoot() {
        return(this.Root);
    }
    
    void setRoot(TrieNode NewRoot) {
        this.Root = NewRoot;
    }
    
    void setIsEndOfWordRoot(boolean value) {
        this.Root.setEndOfWord(value);
    }
    
    void setRoot2() {
        this.Root = new TrieNode();
    }
    
    void PreOrder(TrieNode Node) {
        if( Node == null ) {
            return;
        }
        
        if( Node != getRoot() ) {
            System.out.print(Node.getWord());
        }
        
        if( Node.IsEndOfWord() == true ) {
            System.out.print("# ");
        }
        else if( Node != getRoot() ) {
            System.out.print(" ");
        }
        
        for(int i = 0; i < Alphabet.length; i++) {
            PreOrder(Node.getIndexOfNode(i));
        }
    }
    
    boolean SearchNode(String Word) {
        TrieNode curr = getRoot();
        
        if( curr == null ) {
            return(false);
        }
        
        curr = curr.getIndexOfNode( (Word.charAt(0) - 'a' ) );
        
        int CharsCompared = 0;
        int i;
        TrieNode prev = curr;
        while( curr != null ) {
            
            for(i = 0; i < curr.getWordLength() && CharsCompared < Word.length() ; i++,CharsCompared++) {
                
                if( Word.charAt(CharsCompared) != curr.getLetter(i) ) {
                    return(false);
                }
            }
            
            if( CharsCompared < Word.length() && i == curr.getWordLength() ) {
                prev = curr;
                curr = curr.getIndexOfNode(Word.charAt(CharsCompared) -'a');
            }
            else if( CharsCompared == Word.length() && i < curr.getWordLength() ) {
                return(false);
            }
            else {
                return(curr.IsEndOfWord());
            }
        }
        return( CharsCompared == Word.length() && prev.IsEndOfWord() );
        //return(false);
    }
    
     
    
    int InsertNode(String Word) {
        int i,j;
        for(i = 0; i < Word.length(); i++) {
            for(j = 0; j < Alphabet.length && Word.charAt(i) != Alphabet[j];j++) {}
            if( j == Alphabet.length ) {
                return(-1);
            }
        }
        
        if( SearchNode(Word) == true ) {
            return(0);
        }
        
        TrieNode curr = getRoot();
        if( curr == null ) {
             setRoot2();
             TrieNode NewNode = new TrieNode(Word);
             TrieNode root = getRoot();
             root.setChild( ( Word.charAt(0) -'a' ) , NewNode);
             NewNode.setParent(root);
            return(1);
        }
        
        curr = curr.getIndexOfNode( (Word.charAt(0) - 'a') );
        
        int CharsCompared = 0;
        TrieNode prev = curr;
        
        if( curr == null ) {
            prev = getRoot();
        }
        
        while( curr != null && CharsCompared < Word.length() ) {
            for(i = 0; i < curr.getWordLength() && CharsCompared < Word.length(); i++,CharsCompared++) {
                if( curr.getLetter(i) != Word.charAt(CharsCompared) ) {
                    break;
                }
            }
            
            if( i < curr.getWordLength() ) {
                if( CharsCompared < Word.length() ) {
                   String S1 = curr.getSubstring(i);
                   String S2 = Word.substring(CharsCompared);
                   
                   
                   TrieNode NewNode1 = new TrieNode(S1);
                   TrieNode NewNode2 = new TrieNode(S2);
                   
                   NewNode1.setEndOfWord(curr.IsEndOfWord());
                   curr.setWord(curr.getWord().substring(0,i));
                   curr.setEndOfWord(false);
                   
                   NewNode1.setParent(curr);
                   NewNode2.setParent(curr);
                   curr.setEndOfWord(false);
                   
                   if( NewNode1.getParent() != null ) {
                       for( j = 0; j < Alphabet.length ; j++) {
                           
                           if( curr.getIndexOfNode(j) != null && curr.getIndexOfNode(j) != NewNode1 ) {
                               TrieNode tmp = curr.getIndexOfNode(j);
                               NewNode1.setChild( ( tmp.getLetter(0) - 'a' ) , tmp);
                               
                               curr.setChild( ( tmp.getLetter(0) - 'a' ) , null);
                               tmp.setParent(NewNode1);
                               curr.setEndOfWord(false);
                           }
                           
                       }
                       curr.setChild((S1.charAt(0) - 'a' ),NewNode1);
                       curr.setChild((S2.charAt(0) - 'a' ),NewNode2);
                   }
                                      
                }
                else {
                   String S = curr.getSubstring(i);
                   TrieNode NewNode = new TrieNode(S);
                   
                   for(int k = 0; k < Alphabet.length; k++) {
                       TrieNode tmp = curr.getIndexOfNode(k);
                       if( tmp != null ) {
                           NewNode.setChild( ( tmp.getLetter(0) - 'a' ) , tmp);
                           tmp.setParent(NewNode);
                           
                           curr.setChild( ( tmp.getLetter(0) - 'a' ) , null);
                       }
                   }
                   NewNode.setEndOfWord(curr.IsEndOfWord());
                   curr.setEndOfWord(true);
                   curr.setWord(curr.getWord().substring(0,i));
                   curr.setChild((S.charAt(0) - 'a' ),NewNode);
                   NewNode.setParent(curr);
                  
                }
                return(1);
            }
            else if( CharsCompared < Word.length() ) { 
                prev = curr;
                curr = curr.getIndexOfNode( (Word.charAt(CharsCompared) - 'a') );
            }
        }
        
        if( CharsCompared < Word.length() ) {
            TrieNode NewNode = new TrieNode(Word.substring(CharsCompared));
            prev.setChild((Word.charAt(CharsCompared) - 'a'),NewNode);
            NewNode.setParent(prev);
        }
        else {
            if( curr != null ) {
                curr.setEndOfWord(true);
            }
            else {
                prev.setEndOfWord(true);
            }
        }
        
        return(1);
    }
    
    int DeleteNode(String Word) {
        TrieNode curr = getRoot();
        
        if( curr == null ) {
            return(0);
        }
        
        if( SearchNode(Word) == false ) {
            return(0);
        }
        
        curr = curr.getIndexOfNode( (Word.charAt(0) - 'a') );
        
        int CharsCompared = 0;
        int i;
        
        while( curr != null && CharsCompared < Word.length() ) {
            for(i = 0; i < curr.getWordLength() && CharsCompared < Word.length() ; i++,CharsCompared++) {
                if( Word.charAt(CharsCompared) != curr.getLetter(i) ) {
                    //return(0);
                    break;
                }
            }
            
            if( CharsCompared < Word.length() && i == curr.getWordLength() ) {
                curr = curr.getIndexOfNode(Word.charAt(CharsCompared) -'a' );
                continue;
            }
            
            TrieNode ParentOfDeleted = curr.getParent();
            //boolean DeletedANode = false;
            
            curr.setEndOfWord(false);
            
            if( curr == getRoot() ) {
                setRoot(null);
                return(1);
            }
            
            
            while( ParentOfDeleted != null ) { 
                //DeletedANode = true;
                
                if( curr.CountChildren() == 1 && curr.IsEndOfWord() == false ) {
                    int k;
                    for( k = 0; k < Alphabet.length && curr.getIndexOfNode(k) == null; k++ ) {}
                        TrieNode SingleChild = curr.getIndexOfNode(k);
                        
                        if( curr != getRoot() ) {
                            SingleChild.MergeWithParent();
                        }
                }
                else if( curr.CountChildren() == 0 && curr.IsEndOfWord() == false ) {
                    curr.setParent(null);
                    ParentOfDeleted.setChild( (curr.getLetter(0) - 'a') , null);
                }
                
                
                if( ParentOfDeleted.CountChildren() == 1 && ParentOfDeleted.IsEndOfWord() == false ) {
                    int k;
                    for( k = 0; k < Alphabet.length && ParentOfDeleted.getIndexOfNode(k) == null; k++ ) {}
                    TrieNode SingleChild = ParentOfDeleted.getIndexOfNode(k);
                    
                    if( ParentOfDeleted != getRoot() ) {
                        SingleChild.MergeWithParent();
                    }
                }
                
                curr = ParentOfDeleted;
                ParentOfDeleted = ParentOfDeleted.getParent();
            }
            TrieNode root = getRoot();
            for(int j = 0; j < Alphabet.length; j++) {
                TrieNode tmp = root.getIndexOfNode(j);
                if( tmp != null && tmp.CountChildren() == 0 && tmp.IsEndOfWord() == false ) {
                    tmp.setParent(null);
                    root.setChild( ( tmp.getLetter(j) - 'a' ) , null);
                }
            }
            
            return(1);
        }
       return(0);
    }
    
    void Dictionary(TrieNode Node, StringBuffer Word) {
        if( Node == null ) {
            return;
        }
        
        if( Node.IsEndOfWord() == true ) {
            System.out.println( Word );
        }
        
        for(int i = 0; i < Alphabet.length; i++) {
            TrieNode curr = null;
            if( Node.getIndexOfNode(i) != null ) {
                curr = Node.getIndexOfNode(i);
                Word = Word.append(curr.getWord());
            }
           
            
            Dictionary(Node.getIndexOfNode(i), Word);
            
            if( curr != null ) {
                String tmp = new String(curr.getWord());

                int StartIndex = max( Word.lastIndexOf(tmp) , 0 );
                Word = Word.delete( StartIndex , ( StartIndex + ( Word.length() ) ) );
                
            }
        }
    }
    
    void WordsWithSuffix(TrieNode Node,StringBuffer Word,String Suffix) {
        if( Node == null ) {
            return;
        }
      
        
        if( Node.IsEndOfWord() == true ) {
            int j = Suffix.length() - 1;
            for( int k = Word.length() - 1; k >= 0 && j >= 0 && Word.charAt(k) == Suffix.charAt(j); k--,j--) {}
            
            if( j < 0 ) {
                System.out.println( Word );
            }
        }
        
        for(int i = 0; i < Alphabet.length; i++) {
            TrieNode curr = null;
            if( Node.getIndexOfNode(i) != null ) {
                curr = Node.getIndexOfNode(i);
                Word = Word.append(curr.getWord());
            }
            
            WordsWithSuffix(Node.getIndexOfNode(i), Word, Suffix);
            
            if( curr != null ) {
                String tmp = new String(curr.getWord());
                int StartIndex = max( Word.lastIndexOf(tmp) , 0 );
                Word = Word.delete( StartIndex , ( StartIndex + ( Word.length() ) ) );
            }
        }
    }
    
    void DistantWords(TrieNode Node, StringBuffer Word, String DistantWord, int RequiredDistance) {
        if( Node == null ) {
            return;
        }
      
        
        if( Node.IsEndOfWord() == true && Word.length() == DistantWord.length() ) {
            int Distance = 0;
            for(int k = 0; k < DistantWord.length(); k++) {
                if( Word.charAt(k) != DistantWord.charAt(k) ) {
                    Distance++;
                }
            }
            if( Distance == RequiredDistance ) {
                System.out.println( Word );
            }
        }
        
        for(int i = 0; i < Alphabet.length; i++) {
            TrieNode curr = null;
            if( Node.getIndexOfNode(i) != null ) {
                curr = Node.getIndexOfNode(i);
                Word = Word.append(curr.getWord());
            }
            
            DistantWords(Node.getIndexOfNode(i), Word, DistantWord,RequiredDistance);
            
            if( curr != null ) {
                String tmp = new String(curr.getWord());
                int StartIndex = max( Word.lastIndexOf(tmp) , 0 );
                Word = Word.delete( StartIndex , ( StartIndex + ( Word.length() ) ) );
            }
        }
    }
    
}
