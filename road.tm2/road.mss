// Basic color palette, from which variations will be derived.
@motorway:          #95b499;
@main:              #fdf5d6;
@street:            #fff;
@street_limited:    #f3f3f3;

// ---------------------------------------------------------------------

// Roads are split across 3 layers: #road, #bridge, and #tunnel. Each
// road segment will only exist in one of the three layers. The
// #bridge layer makes use of Mapnik's group-by rendering mode;
// attachments in this layer will be grouped by layer for appropriate
// rendering of multi-level overpasses.

// The main road style is for all 3 road layers and divided into 2 main
// attachments. The 'case' attachment is 

#road, #bridge, #tunnel {
  
  // casing/outlines & single lines
    
  //motorways
  ::case[zoom>=3]['mapnik::geometry_type'=2] {
    [class='motorway'] {
      line-join:round;
      line-color: mix(@motorway, #6f4e50, 75);
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      [zoom>=5]  { line-width:0.5; }
      [zoom>=6]  { line-width:1; }
      [zoom>=7]  { line-width:1.5; }
      [zoom>=8] { line-width:2.5; }
      [zoom>=10]  { line-width:3; }
      [zoom>=13] { line-width:3.5;  }
      [zoom>=14] { line-width:5; }
      [zoom>=15] { line-width:7; }
      [zoom>=16] { line-width:9; }
    }
    
    // interchanges/onramps
    [class='motorway_link'][zoom>=13] {
      line-join:round;
      line-color: mix(@motorway, #800, 75);
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      [zoom>=13] { line-width:1; }
      [zoom>=14] { line-width:3; }
      [zoom>=15] { line-width:5; }
      [zoom>=16] { line-width:6.5; }
    }
    
    // trunk roads (subset of "main" class in mapbox streets)
    [type='trunk'] {
      line-join:round;
      line-color: mix(@main, #800, 75);
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      [zoom>=2] { line-width:2; }
      [zoom>=10] { line-width:2.4; }
      [zoom>=13] { line-width:2.5; }
      [zoom>=14] { line-width:4; }
      [zoom>=15] { line-width:5; }
      [zoom>=16] { line-width:8; }
    }
    
    // Primary roads (subset of "main" class in mapbox streets)
    [type='primary']{
      line-join:round;
      line-color: @land * 0.8;
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      [zoom>=6] { line-width:0.2; }
      [zoom>=7] { line-width:0.4; }
      [zoom>=8] { line-width:1.5; }
      [zoom>=10] { line-width:2.4; }
      [zoom>=13] { line-width:4.5; }
      [zoom>=14] { line-width:5.5; }
      [zoom>=15] { line-width:6.5; }
      [zoom>=16] { line-width:8; }
      [zoom>=17] { line-width:9; }
    }
    
    // Secondary/tertiary roads (subset of "main" class in mapbox streets)
    [type='secondary'],
    [type='tertiary']{
      line-join:round;
      line-color: @land * 0.8;
      line-width:1;
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      [zoom>=13] { line-width:2.5; }
      [zoom>=14] { line-width:4; }
      [zoom>=15] { line-width:5; }
      [zoom>=16] { line-width:7; }
      [zoom>=17] { line-width:8; }
    }
    
    //residual main roads that somehow don't have a type
    [class='main'] [type=''] {
      line-join:round;
      line-color: #aaa;
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      [zoom>=2] { line-width:0.5; }
      [zoom>=9] { line-width:0; }
    }    
    // streets
    [class='street'],[class='street_limited'][zoom>=10] {
      line-join:round;
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      line-color: @land * 0.8;
      [zoom>=10] { line-width:0.5; }
      [zoom>=14] { line-width:3; }
      [zoom>=15] { line-width:4; }
      [zoom>=16] { line-width:6; }
    }
    
    // service roads
    [class='service'][zoom>=15] {
      line-join:round;
      #road { line-cap: round; }
      #tunnel { line-dasharray:3,2; }
      line-color: @land * 0.9;
      [zoom>=15] { line-width:1; }
      [zoom>=16] { line-width:4; }
    }
    
    //walking paths (just a dotted line, so this won't have an inline)
    [class='path'][zoom>=14] {
      line-color: #aaa;
      line-dasharray: 2,1;
      line-width:0.5;
      [zoom>=15] { line-width:1; }
      [zoom>=17] { line-width:1.5; }    
    }
  }
  
  // fill/inlines

  ::fill[zoom>=6]['mapnik::geometry_type'=2] {
    [class='motorway'][zoom>=8] {
      line-join:round;
      #road, #bridge { line-cap:round; }
      line-color:@motorway;
      #tunnel { line-color:lighten(@motorway,4); }
      [zoom>=8] { line-width:1.5; }
      [zoom>=10] { line-width:2; }
      [zoom>=13] { line-width:2.5; }
      [zoom>=14] { line-width:3.5; }
      [zoom>=15] { line-width:5; }
      [zoom>=16] { line-width:7; }
    }
    [class='motorway_link'][zoom>=14] {
      line-join:round;
      #road, #bridge { line-cap: round; }
      line-color:@motorway;
      #tunnel {  line-color:lighten(@motorway,4); }
      [zoom>=14] { line-width:1.5; }
      [zoom>=15] { line-width:3; }
      [zoom>=16] { line-width:4.5; }
    }
    [type='trunk'] {
      line-join:round;
      line-color:@main;
      line-width:0.5;
      [zoom>=10] { line-width:1; }
      [zoom>=13] { line-width:1.5; }
      [zoom>=14] { line-width:2.5; }
      [zoom>=15] { line-width:3.5; }
      [zoom>=16] { line-width:6.5; }
      #road, #bridge { line-cap: round; }
      #tunnel { line-color:lighten(@main,4); }
    }

    [type='primary'][zoom>=8] {
      line-join:round;
      line-color:white;
      [zoom>=8] { line-width:0.5; }
      [zoom>=10] { line-width:1; }
      [zoom>=12] { line-width:1.5; }
      [zoom>=13] { line-width:3; }
      [zoom>=14] { line-width:4; }
      [zoom>=15] { line-width:4.5; }
      [zoom>=16] { line-width:5.5; }
      [zoom>=17] { line-width:7; }

      #road, #bridge { line-cap: round; }
      #tunnel { line-color:white; }
    }
    
    [type='secondary'],
    [type='tertiary'] {
      line-join:round;
      #road, #bridge { line-cap: round; }
      line-color:#fff;
      line-width:0.5;
      #tunnel { line-color:lighten(@main,4); }
      [zoom>=13] { line-width:1.5; }
      [zoom>=14] { line-width:2.5; }
      [zoom>=15] { line-width:3.5; }
      [zoom>=16] { line-width:4.5; }
      [zoom>=17] { line-width:6; }
    }
    
    [class='street'],[class='street_limited'][zoom>=14] {
      line-join:round;
      line-color:#fff;
      line-width:0;
      #road, #bridge { line-cap: round; }
      [zoom>=14] { line-width:2;  }
      [zoom>=15] { line-width:2.5;  }
      [zoom>=16] { line-width:4; }
    }

    [class='service'][zoom>=16], {
      line-join:round;
      #road, #bridge { line-cap: round; }
      [zoom>=16] { line-width:2; line-color:#fff; }
    }
    
    // railroad crosshatches
    [class='major_rail'] {
      line-width: 0.3;
      line-color: #999;
      [zoom>=16] {
      	// Hatching
      	h/line-width: 2;
      	h/line-color: #bbb;
      	h/line-dasharray: 1,10;
      }
    }
  }
}
