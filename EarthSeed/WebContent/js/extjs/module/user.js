 Ext.require(['*']);
    function getStore () {
        var myData = [
            ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
            ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
            ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
            ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
            ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
            ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
            ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
            ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
            ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
            ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am']
        ];

       return Ext.create('Ext.data.ArrayStore', {
           fields: [
              {name: 'company'},
              {name: 'price', type: 'float', convert: null},
              {name: 'change', type: 'float', convert: null},
              {name: 'pctChange', type: 'float', convert: null},
              {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
           ],
           sorters: {
               property : 'company',
               direction: 'ASC'
           },
           data: myData,
           pageSize: 8
       });
   }

   function getGrid () {
       var store = getStore(),
           pagingBar = Ext.createWidget('pagingtoolbar', {
               store      : store,
               displayInfo: true,
               displayMsg : 'Displaying topics {0} - {1} of {2}'
           });

       return {
           xtype: 'gridpanel',

           height: '100%',
           width : '100%',

           title: 'UserLists',
           collapsible: true,

           store: store,

           columns: [
               {header: "Company",      flex: 1, sortable: true, dataIndex: 'company'},
               {header: "Price",        width: 75,  sortable: true, dataIndex: 'price'},
               {header: "Change",       width: 75,  sortable: true, dataIndex: 'change'},
               {header: "% Change",     width: 75,  sortable: true, dataIndex: 'pctChange'},
               {header: "Last Updated", width: 85,  sortable: true, xtype: 'datecolumn', dataIndex: 'lastChange'}
           ],
           loadMask: true,

           viewConfig: {
               stripeRows: true
           },

           bbar: pagingBar,
           tbar: [
               {text: 'Toolbar'},
               '->',
               {
                   xtype: 'triggerfield',
                   trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
                   trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger'
               }
           ]
       };
   }
    Ext.onReady(function() {

        Ext.QuickTips.init();
        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));

        var viewport = Ext.create('Ext.Viewport', {
            id: 'border-example',
            layout: 'border',
            items: [
            // create instance immediately
            Ext.create('Ext.Component', {
                region: 'north',
                height: 32, // give north and south regions a height
                autoEl: {
                    tag: 'div',
                    html:'<p>north - generally for menus, toolbars and/or advertisements</p>'
                }
            }), {
                // lazily created panel (xtype:'panel' is default)
                region: 'south',
                contentEl: 'south',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                collapsed: true,
                title: 'South',
                margins: '0 0 0 0'
            }, {
                xtype: 'tabpanel',
                region: 'east',
                title: 'East Side',
                dockedItems: [{
                    dock: 'top',
                    xtype: 'toolbar',
                    items: [ '->', {
                       xtype: 'button',
                       text: 'test',
                       tooltip: 'Test Button'
                    }]
                }],
                animCollapse: true,
                collapsible: true,
                split: true,
                width: 225, // give east and west regions a width
                minSize: 175,
                maxSize: 400,
                margins: '0 5 0 0',
                activeTab: 1,
                tabPosition: 'bottom',
                items: [{
                    html: '<p>A TabPanel component can be a region.</p>',
                    title: 'A Tab',
                    autoScroll: true
                }, Ext.create('Ext.grid.PropertyGrid', {
                        title: 'Property Grid',
                        closable: true,
                        source: {
                            "(name)": "Properties Grid",
                            "grouping": false,
                            "autoFitColumns": true,
                            "productionQuality": false,
                            "created": Ext.Date.parse('10/15/2006', 'm/d/Y'),
                            "tested": false,
                            "version": 0.01,
                            "borderWidth": 1
                        }
                    })]
            }, {
                region: 'west',
                stateId: 'navigation-panel',
                id: 'west-panel', // see Ext.getCmp() below
                title: 'West',
                split: true,
                width: 200,
                minWidth: 175,
                maxWidth: 400,
                collapsible: true,
                animCollapse: true,
                margins: '0 0 0 5',
                layout: 'accordion',
                items: [{
                    contentEl: 'west',
                    title: 'Navigation',
                    iconCls: 'nav' // see the HEAD section for style used
                }, {
                    title: 'Settings',
                    html: '<p>Some settings in here.</p>',
                    iconCls: 'settings'
                }, {
                    title: 'Information',
                    html: '<p>Some info in here.</p>',
                    iconCls: 'info'
                }]
            },
            // in this instance the TabPanel is not wrapped by another panel
            // since no title is needed, this Panel is added directly
            // as a Container
            Ext.create('Ext.tab.Panel', {
                region: 'center', // a center region is ALWAYS required for border layout
                deferredRender: false,
                activeTab: 0,     // first tab initially active
                items: [ {
                    contentEl: 'center2',
                    title: 'UserAccounts',
                    items: getGrid (),
                    autoScroll: true
                }]
            })]
        });
        
    });