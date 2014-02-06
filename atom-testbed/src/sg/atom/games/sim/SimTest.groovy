package sg.atom.ai.testbed.games.sim

import sg.atom.ai.testbed.decision.goalbase.*
/**
 *
 * @author cuong.nguyenmanh2
 */
// Desire
hungry = new Goal(name:"Goal",value:1)
boring = new Goal(name:"Boring",value:0.5)
lonely = new Goal(name:"Lonely",value:0.2)

// Actions can take .

// MONEY
work

// LOVE
meet

love

sex

threeSome

kiss

// FOOD

cook

eat

shit

drink

// LOOK AND HEALTH
outside

workout

makeup 

//
playgame = new Action(
    name:"Playgame",
    description:"Play video game",
    change:[fun:0.5],
    script:{ who->
        
    })

phone = new Action(
    name:"Phone",
    description:"Talk on the phone",
    change:[fun:0.2]
)


// Event the world give

