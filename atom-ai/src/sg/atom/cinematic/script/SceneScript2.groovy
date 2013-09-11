package sg.atom.cinematic.script

/**
 * Cinematic for the game
 * Script 1.
 * @author cuong.nguyenmanh2
 */

cine.builder.script{
    characters{
        john= male(name:"John",who:"simpleman/simpleMan.j3o",aniRoot:"man")
        aki= female(name:"Aki",who:"simple_girl/simple_girl.j3o",aniRoot:"woman")
    }
    
       scenes{
        scene(name:"scene1"){
            
            shot{
                name "shot1"
                
                cam {
                    pos vec3(1,2,2)
                    dialog with:[john,aki]
                }
                
                /*
                preset "dialogue 3"
                
                act do:{
                john walk
                aki walk
                aki.talk "Hello dear",john
                }
                
                 */
                
                act do:{
                    john.pos(7,0,1)
                    //john.target(2,0,5)
                    aki.pos(2,0,5)
                    /*
                    john.anim "walk"
                    aki.anim "walk"
                    aki.lookAt john
                    john.lookAt aki
                    */
                   john.walkTo aki,1
                   aki.walkTo john,1
                }
                    
                act do:{
                    sleep 4000
                    //john.wave()
                    john.say "Hi Aki"
                    sleep 1000
                    //aki.anim "wave"
                    aki.say "Hi John"
                    aki.say "Long time no see"
                    aki.say "I miss you so much"
                    john.say "Time change"
                    john.say "Should be done"

                }
                    
            }
        }
    }

}

    
