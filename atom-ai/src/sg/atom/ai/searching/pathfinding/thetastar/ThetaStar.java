/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.thetastar;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ThetaStar {
}
/*
 * package
{
    import com.bit101.components.CheckBox;
    import com.bit101.components.Label;
    
    import flash.display.Graphics;
    import flash.display.Sprite;
    import flash.events.Event;
    import flash.events.MouseEvent;
    import flash.utils.Dictionary;
    import flash.utils.getTimer;

    [SWF(width="800", height="600", frameRate=24)]
    public class ThetaPath extends Sprite
    {
        private var _hw:int = Tile.WIDTH / 2;
        private var _hh:int = Tile.HEIGHT / 2;
        private var _pathLine:Sprite;

        private var _open:Vector.<Tile>;
        private var _closed:Vector.<Tile>;
        private var _path:Vector.<Tile>;

        private var _straightCost:int = 1;
        private var _diagCost:int = 2;

        private var _map:Map;
        private var _endTile:Tile;
        private var _startTile:Tile;

        private var _theta:Boolean;
        private var _label:Label;

        public function ThetaPath()
        {
            addEventListener(Event.ADDED_TO_STAGE, init);
        }

        private function init(event:Event):void
        {
            _map = new Map();
            _map.y = 30;
            addChild(_map);

            _pathLine = new Sprite();
            _pathLine.y = 30;
            addChild(_pathLine);

            var checkbox:CheckBox = new CheckBox(this, 10, 10, "Use Theta", toggleTheta);
            _theta = true;
            checkbox.selected = _theta;
            
            _label = new Label(this, 150, 10);

            _map.addEventListener(MouseEvent.CLICK, addPoint);
        }

        private function addPoint(event:MouseEvent):void
        {
            var _nx:int = Math.floor(_map.mouseX / Tile.WIDTH);
            var _ny:int = Math.floor(_map.mouseY / Tile.HEIGHT);
            if (event.ctrlKey)
                _endTile = _map.getTile(_nx, _ny);
            else
                _startTile = _map.getTile(_nx, _ny);

            findPath();
        }

        public function findPath():void
        {
            if (_startTile && _endTile)
            {
                _open = new Vector.<Tile>();
                _closed = new Vector.<Tile>();

                _startTile.previous = _startTile;
                _startTile.g = 0;
                _startTile.h = euclidian(_startTile);
                _startTile.f = _startTile.h;
                
                _map.resetColors();
                var _time:uint = getTimer();
                if (search())
                {
                    var i:uint, len:uint = _path.length;
                    var g:Graphics = _pathLine.graphics;
                    g.clear();
                    g.lineStyle(1, 0xFF0000);
                    g.moveTo(_path[0].x + _hw, _path[0].y + _hh);
                    _path[0].color = 0xFFCCCC;
                    for (i = 1; i < len; i++)
                    {
                        _path[i].color = 0xFFCCCC;
                        g.lineTo(_path[i].x + _hw, _path[i].y + _hh);
                    }
                    
                    _label.text = "Time in ms: " + String(getTimer() - _time);
                }
            }
        }

        public function search():Boolean
        {
            var i:uint, j:uint;
            var g:Number, h:Number, f:Number;
            var startX:int, endX:int;
            var startY:int, endY:int;
            var cost:int;
            var neighboor:Tile, _previous:Tile;
            var node:Tile = _startTile;
            while (node != _endTile)
            {
                startX = Math.max(0, node.tx - 1);
                endX = Math.min(Map.SIZEX - 1, node.tx + 1);
                startY = Math.max(0, node.ty - 1);
                endY = Math.min(Map.SIZEY - 1, node.ty + 1);

                for (i = startX; i <= endX; i++)
                {
                    for (j = startY; j <= endY; j++)
                    {
                        neighboor = _map.getTile(i, j);
                        if (neighboor == node || !neighboor.walkable)
//                            || !_map.getTile(node.tx, neighboor.ty).walkable
//                             || !_map.getTile(neighboor.tx, node.ty).walkable
                            continue;

                        _previous = node;

                        if (_theta && raytrace(node.previous, neighboor))
                        {
                            cost = euclidian(node.previous, neighboor);
                            g = node.previous.g + cost;
                            h = euclidian(neighboor);
                            f = g + h;
                            _previous = node.previous;
                        }
                        else
                        {
                            cost = ((node.x != neighboor.x) || (node.y != neighboor.y)) ? _diagCost : _straightCost;
                            g = node.g + cost;
                            h = euclidian(neighboor);
                            f = g + h;
                        }

                        if (isOpen(neighboor) || isClosed(neighboor))
                        {
                            if (neighboor.f > f)
                            {
                                neighboor.f = f;
                                neighboor.g = g;
                                neighboor.h = h;
                                neighboor.previous = _previous;
                            }
                        }
                        else
                        {
                            neighboor.f = f;
                            neighboor.g = g;
                            neighboor.h = h;
                            neighboor.previous = _previous;
                            _open.push(neighboor);
                        }
                    }
                }

                _closed.push(node);
                if (_open.length == 0)
                {
                    trace("no path found");
                    return false;
                }

                _open.sort(sortF);
                node = _open.shift() as Tile;
            }
            buildPath();
            return true;
        }

        private function buildPath():void
        {
            _path = new Vector.<Tile>();
            var node:Tile = _endTile;
            _path.push(node);
            while (node != _startTile)
            {
                node = node.previous;
                _path.unshift(node);
            }
        }

        private function toggleTheta(e:Event):void
        {
            var check:CheckBox = CheckBox(e.currentTarget);
            _theta = check.selected;
            findPath();
        }

        private function isOpen(node:Tile):Boolean
        {
            return _open.indexOf(node) != -1;
        }

        private function isClosed(node:Tile):Boolean
        {
            return _closed.indexOf(node) != -1;
        }

        private function manhattan(node:Tile):Number
        {
            return Math.abs(node.tx - _endTile.tx) * _straightCost + Math.abs(node.ty + _endTile.ty) * _straightCost;
        }

        private function euclidian(node:Tile, _end:Tile = null):Number
        {
            if (!_end)
                _end = _endTile;
            var dx:Number = node.tx - _end.tx;
            var dy:Number = node.ty - _end.ty;
            return Math.sqrt(dx * dx + dy * dy) * _straightCost;
        }

        private function diagonal(node:Tile):Number
        {
            var dx:Number = Math.abs(node.tx - _endTile.tx);
            var dy:Number = Math.abs(node.ty - _endTile.ty);
            var diag:Number = Math.min(dx, dy);
            var straight:Number = dx + dy;
            return _diagCost * diag + _straightCost * (straight - 2 * diag);
        }

        public function raytrace(start:Tile, end:Tile):Boolean
        {
            var x0:int = start.tx, y0:int = start.ty;
            var x1:int = end.tx, y1:int = end.ty;
            var dx:int = Math.abs(x1 - x0), dy:int = Math.abs(y1 - y0);

            var _x:int = x0, _y:int = y0;
            var x_inc:int = (x1 > x0) ? 1 : -1;
            var y_inc:int = (y1 > y0) ? 1 : -1;
            var error:int = dx - dy;

            for (var n:int = dx + dy; n > 0; --n)
            {
                var _tile:Tile = _map.getTile(_x, _y);
                //_tile.color = 0xCCFFCC;
                if (!_tile.walkable)
                    return false;

                if (error > 0)
                {
                    _x += x_inc;
                    error -= dy;
                }
                else
                {
                    _y += y_inc;
                    error += dx;
                }
            }
            return true;
        }

        private function sortF(p1:Tile, p2:Tile):int
        {
            return p1.f - p2.f;
        }
    }
}

class Map extends Sprite
{
    private var _staticMap:Array;

    public static const SIZEX:uint = 40;
    public static const SIZEY:uint = 34;

    private var _map:Vector.<Vector.<Tile>>;

    public function Map()
    {
        // Static map
        _staticMap = [];

        _staticMap.push([ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ]);
        _staticMap.push([ 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1 ]);
        _staticMap.push([ 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1 ]);
        _staticMap.push([ 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1 ]);
        _staticMap.push([ 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 ]);
        _staticMap.push([ 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1 ]);
        _staticMap.push([ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 ]);
        _staticMap.push([ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ]);

        generateMap();
    }

    public function getTile(_x:int, _y:int):Tile
    {
        return _map[_y][_x];
    }
    
    public function resetColors():void
    {
        var i:uint, j:uint;
        for (i = 0; i < SIZEY; i++)
            for (j = 0; j < SIZEX; j++)
                _map[i][j].resetColor();
    }

    private function generateMap():void
    {
        _map = new Vector.<Vector.<Tile>>();
        var _tile:Tile;
        var i:uint, j:uint;
        for (i = 0; i < SIZEY; i++)
        {
            _map.push(new Vector.<Tile>(SIZEX));
            for (j = 0; j < SIZEX; j++)
            {
                if (_staticMap[i][j] == Tile.STONE)
                    _tile = new Tile(Tile.STONE, false);
                else
                    _tile = new Tile(Tile.EMTPY);
                // Add sprite
                _tile.tx = j;
                _tile.ty = i;
                this.addChild(_tile);
                // Add to vector
                _map[i][j] = _tile;
            }
        }
    }
}

import flash.display.Graphics;
import flash.display.Sprite;

class Tile extends Sprite
{
    // Tile dimensions
    public static var WIDTH:uint = 15;
    public static var HEIGHT:uint = 15;

    // Tile types
    public static const EMTPY:uint = 0;
    public static const STONE:uint = 1;

    private var _type:uint;
    private var _walkable:Boolean;

    private var _tx:uint;
    private var _ty:uint;

    private var _color:uint;

    public var g:Number;
    public var h:Number;
    public var f:Number;
    public var previous:Tile;

    public function Tile(_type:uint, _walk:Boolean = true)
    {
        type = _type;
        walkable = _walk;
    }

    private function draw():void
    {
        // Draw
        var g:Graphics = this.graphics;
        g.clear();
        g.lineStyle(1, 0x666666, alpha);
        g.beginFill(_color, alpha);
        g.drawRect(0, 0, WIDTH, HEIGHT);
        g.endFill();
    }

    public function get tx():uint
    {
        return _tx;
    }

    public function set tx(value:uint):void
    {
        _tx = value;
        x = value * WIDTH;
    }

    public function get ty():uint
    {
        return _ty;
    }

    public function set ty(value:uint):void
    {
        _ty = value;
        y = value * HEIGHT;
    }

    public function get type():uint
    {
        return _type;
    }

    public function set type(value:uint):void
    {
        _type = value;
        resetColor();
    }

    public function get color():uint
    {
        return _color;
    }

    public function set color(value:uint):void
    {
        _color = value;
        draw();
    }
    
    public function resetColor():void
    {
        switch (_type)
        {
            case EMTPY:
                color = 0xFFFFFF;
                break;
            case STONE:
                color = 0x4C4C4C;
                break;
        }
    }

    public function get walkable():Boolean
    {
        return _walkable;
    }

    public function set walkable(value:Boolean):void
    {
        _walkable = value;
    }
}
 */