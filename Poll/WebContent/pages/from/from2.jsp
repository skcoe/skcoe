
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../../../templete/libinclude.jsp" %>

<title>From</title>
</head>
<body  bgcolor="#1475A3">
<script type="text/javascript">


Ext.onReady(function() {
 
    var configs = [{
        width: 800,
        height: 800,
        border: true,
        frame: true,
        title: 'Login',
        items: [
                {
                    fieldLabel: 'Username',
                    xtype     : 'textfield',
                    name      : 'someField',
                    //emptyText : 'Enter a value',
                    anchor    : '100%'
                },{
                    fieldLabel: 'Password',
                    xtype     : 'textfield',
                    inputType: 'password',
                    name      : 'someField',
                   // emptyText : 'Enter a value',
                    anchor    : '100%'
                },
                {
                    fieldLabel: 'User type',
                    xtype     : 'combo',
                    store     : ['a', 'Bar'],
                    anchor    : '100%'
                }],
                buttons: [
                          {
                              text   : 'login',
                              id     : 'message_box',
                              handler: function() {
                                  Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?');
                              }
                          }
                      ]
    }];
    
    Ext.each(configs, function(config) {
        var element = Ext.getBody().createChild({cls: 'panel-container'});
        
        Ext.createWidget('panel', Ext.applyIf(config, {
            renderTo: element,
            bodyPadding: 7
        }));
    });
});

</script>

</body>
</html>