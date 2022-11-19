/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;

import javafx.scene.image.Image;

/**
 *
 * @author Daniel Rodríguez Hernando
 */
public class AuxFunctions {
    
    //Fonction pour initialiser un tableau d'images avec toutes les images qui seront utilisées dans l'affichage des inventaires
    public static Image[] loadImages()
    {
        Image key_image = new Image("/LostSquad/View/AllView/img/key.png", 50, 50, true, true);
        Image kevlar_image = new Image("/LostSquad/View/AllView/img/kevlar.png", 50, 50, true, true);
        Image weapon_image = new Image("/LostSquad/View/AllView/img/knife.png", 50, 50, true, true);
        Image charger_image = new Image("/LostSquad/View/AllView/img/charger.png", 150, 150, true, true);
        Image ammo_image = new Image("/LostSquad/View/AllView/img/ammo.png", 50, 50, true, true);
        Image grip_image = new Image("/LostSquad/View/AllView/img/grip.png", 50, 50, true, true);
        Image cross_image = new Image("/LostSquad/View/AllView/img/cross.png", 50, 50, true, true);
        Image barrel_image = new Image("/LostSquad/View/AllView/img/barrel.png", 50, 50, true, true);
        Image sight_image = new Image("/LostSquad/View/AllView/img/sight.png", 50, 50, true, true);
        Image fireArmWithMod_image = new Image("/LostSquad/View/AllView/img/bigFireArm.png", 70, 70, true, true);
        Image fireArm_image = new Image("/LostSquad/View/AllView/img/smallFireArm.png", 50, 50, true, true);
        Image heal_image = new Image("/LostSquad/View/AllView/img/heal.png", 50, 50, true, true);
        Image shield_image = new Image("/LostSquad/View/AllView/img/shield.png", 150, 150, true, true);
        Image heart_image = new Image("/LostSquad/View/AllView/img/heart.png", 50, 50, true, true);
        Image check_image = new Image("/LostSquad/View/AllView/img/check.png", 50, 50, true, true);
        Image box_image = new Image("/LostSquad/View/AllView/img/box.png", 100, 100, true, true);
        Image general_image = new Image("/LostSquad/View/AllView/img/general.png", 40, 40, true, true);
        Image[] tab_images = new Image[17];
        tab_images = new Image[]{general_image, box_image, check_image, heart_image, shield_image, heal_image, fireArm_image, fireArmWithMod_image, sight_image, barrel_image, cross_image, grip_image, ammo_image, charger_image, weapon_image, kevlar_image, key_image};
        return tab_images;
    }
}
