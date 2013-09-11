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
    
    environment{
        prop "house"
        /*
        bed
        table
         */
    }
    
    sounds{
        bgsound = music path:"Sounds/Music/love.ogg" , loop:true
        bird = soundfx path:"Sounds/Fx/bird.ogg"
    }
    scenes{
        scene(name:"scene1"){
            splay bgsound
            splay bird
               
            shot{
                name "intro"
                loc where:"building", type:outdoor
                    
                cam{
                    pos vec3(8,3,2)
                    look where:"building@room2"
                    pan type:smooth , duration:3
                }
            }
            
            shot{
                name "shot1"
                loc where:"house", type:indoor
                light "up"
                
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
                    john.anim "walk"
                    aki.anim "walk"
                    //aki.talk "Hello dear",john
                }
                act do:{
                    sleep 4000
                    john.wave()
                    john.say "Hi Aki"
                    sleep 1000
                    aki.anim "wave"
                    aki.say "Hi John"
                }
                    
                cam {
                    dialog with:[john,aki],focus:aki
                }
                    
                act do:{
                    sleep 4000
                    john.sad()  
                    aki.hug john
                    aki.say "I love you"
                }
            }
        }
    }

}

    
