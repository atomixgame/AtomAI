package sg.atom.ai.testbed.planning.htn

import sg.atom.ai.testbed.algorimth.graph.*
/**
 *
 * @author cuong.nguyenmanh2
 */
/*
Action (BuyLand, precond:Money,effect: Land && ! Money)
Action (GetLoan , precond:GoodCredit, effect : Money && Mortgage )
Action (BuildHouse, precond:Land, effect : House )
Action (GetPermit, precond:Land, effect : Permit)
Action (HireBuilder , effect : Contract)
Action (Construct, precond:Permit && Contract, effect : HouseBuilt && !Permit)
Action (PayBuilder , precond: Money && HouseBuilt , effect : Money && House && !Contract)

decompose("BuildHose"){
    steps{
        S1: GetPermit,
        S2: HireBuilder,
        S3: Construction,
        S4: PayBuilder
    }
    orderings: {
        Start << S1 << S2 << S3 << S4 << Finish, 
        Start << S2 << S3
    }
    
    links {
        Start>>Land>>S1, 
        Start>>Money>>S4,
        S1>>Permit>>S3,
        S2>>Contract>>S3,
        S3>>HouseBuilt>>S4,
        S4>>House>>Finish,
        S4>>!Money>>Finish
        
    }
}
*/

frame = new JGraphAdapterDemo("Test1");

g = frame.createGraphModel()
["Start","BuildHouse"].each{v->
    g.addVertex(v)
}

frame.init(g)
frame.visible = true
