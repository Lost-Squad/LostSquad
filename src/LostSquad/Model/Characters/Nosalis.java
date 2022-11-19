/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Model.Characters;

import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.Container;

public class Nosalis extends Enemy {
    
    public Nosalis(String name,Container contain){
        super(name,"A ferocious beast, a huge Mutant dog, he is thirsty for blood, turning his back is putting one foot in his grave, agile he can dodge your attacks",0,null,contain,new Weapon("Coups",0,"with immense force he can cut an unprotected man",10,20,10,10));
    }
    
    @Override
    public void hit(int degat){
        int r=(int) (Math.random()*10);
        if(r>7){
            System.out.printf("0 because,%s to dodge your attack", getName());
        }else{
            super.hit(degat);
        }
    }
    
}
