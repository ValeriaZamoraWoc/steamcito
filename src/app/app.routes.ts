import { Routes } from '@angular/router';
import { LoginUsuario } from './pages/login-usuario/login-usuario';
import { RegistroEmpresa } from './pages/registro-empresa/registro-empresa';
import { authGuard } from './services/auth-guard';
import { AgregarUsuarioEmpresaComponent } from './pages/home-dev/agregar-usuario-empresa/agregar-usuario-empresa';

export const routes: Routes = [

 //public
  { path: 'login', component: LoginUsuario },
  { path: 'registro-empresa', component: RegistroEmpresa },
//privado
  {
    path: 'app',
      loadComponent: () =>
        import('./layout/layout-privado')
          .then(m => m.ComponenteLayoutPrivado),
      canActivate: [authGuard],
      children: [
        {
          path: 'admin',
          loadComponent: () =>
          import('./pages/home-admin/home-admin')
          .then(m => m.HomeAdmin),
      children: [
        {
          path: 'Comisiones',
          loadComponent: () =>
            import('./pages/home-admin/peticiones-comisiones/peticiones-comisiones')
              .then(m => m.PeticionesComisionesComponent)
        },
        {
          path: 'juego/:id',
          loadComponent: () =>
            import('./pages/home-admin/juego-detalle-admin/juego-detalle-admin')
              .then(m => m.JuegoDetalleAdminComponent)
        },
        {
          path: 'clasificaciones',
          loadComponent: () =>
            import('./pages/home-admin/gestionar-clasificaciones/gestionar-clasificaciones')
              .then(m => m.GestionarClasificacionesComponent)
        },
        {
        path: 'buscar',
          loadComponent: () =>
            import('./pages/buscador/buscador')
              .then(m => m.BuscadorComponent)
        },
        {
          path: 'editar-banner',
          loadComponent: () =>
            import('./pages/home-admin/editar-banner/editar-banner')
              .then(m => m.EditarBannerComponent)
        },
        {
      path: 'reportes',
      loadComponent: () =>
        import('./pages/home-admin/reportes-admin/reportes-admin')
          .then(m => m.ReportesAdminComponent),
      children: [

        {
          path: 'ganancia-bruta',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/ganancia-bruta/ganancia-bruta')
              .then(m => m.GananciaBrutaComponent)
        },

        {
          path: 'ganancias-empresa',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/ganancias-empresa/ganancias-empresa')
              .then(m => m.GananciasEmpresaComponent)
        },
        {
          path: 'juegos-clasificacion',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/juegos-clasificacion/juegos-clasificacion')
              .then(m => m.JuegosClasificacionComponent)
        },
        {
          path: 'juegos-categoria',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/juegos-categoria/juegos-categoria')
              .then(m => m.JuegosCategoriaComponent)
        },
        {
          path: 'usuarios-compras',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/usuarios-compras/usuarios-compras')
              .then(m => m.UsuariosComprasComponent)
        },
        {
          path: 'mejores-categoria',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/mejores-categoria/mejores-categoria')
              .then(m => m.MejoresCategoriaComponent)
        },
        {
          path: 'mejores-clasificacion',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/mejores-clasificacion/mejores-clasificacion')
              .then(m => m.MejoresClasificacionComponent)
        },
        {
          path: 'usuarios-resenas',
          loadComponent: () =>
            import('./pages/home-admin/reportes-admin/usuarios-resenas/usuarios-resenas')
              .then(m => m.UsuariosResenasComponent)
        }
      ]
    }
      ]
    },
      {
        path: 'home',
        loadComponent: () =>
          import('./pages/home/home')
            .then(m => m.ComponenteHome)
      },
      {
        path: 'home-dev',
        loadComponent: () =>
          import('./pages/home-dev/home-dev')
            .then(m => m.HomeDevComponent),
        children: [
          {
            path: 'subir-juego',
            loadComponent: () =>
              import('./pages/home-dev/subir-juego/subir-juego')
                .then(m => m.SubirJuegoComponent)
          },
          {
            path: 'catalogo',
            loadComponent: () =>
              import('./pages/home-dev/catalogo-empresa/catalogo-empresa')
                .then(m => m.CatalogoEmpresaComponent)
          },
          {
            path: 'juego/:id',
            loadComponent: () =>
              import('./pages/home-dev/juego-detalle-dev/juego-detalle-dev')
                .then(m => m.JuegoDetalleDevComponent)
          },
          {
            path: 'ver-perfil-empresa/:idEmpresa',
            loadComponent: () => 
              import('./pages/home-dev/ver-perfil-empresa/ver-perfil-empresa')
                .then(m => m.VerPerfilEmpresaComponent)
          },
          {
            path: 'editar-juego/:id',
            loadComponent: () =>
              import('./pages/home-dev/editar-juego/editar-juego')
                .then(m => m.EditarJuegoComponent)
          },
          {
            path: 'agregar-usuario-empresa',
            component: AgregarUsuarioEmpresaComponent
          },
          {
            path: 'negociar-comision',
            loadComponent: () =>
              import('./pages/home-dev/negociar-comision/negociar-comision')
                .then(m => m.NegociarComisionComponent)
          },
          {
            path: 'reportes',
            loadComponent: () =>
              import('./pages/home-dev/reportes-dev/reportes-dev')
                .then(m => m.ReportesDesarrolladorComponent),
            children: [
              {
                path: 'ventas',
                loadComponent: () =>
                  import('./pages/home-dev/reportes-dev/ventas/ventas')
                    .then(m => m.ReporteVentasDevComponent)
              },
              {
                path: 'feedback',
                loadComponent: () =>
                  import('./pages/home-dev/reportes-dev/feedback/feedback')
                    .then(m => m.ReporteFeedbackDevComponent),
                children: [
                  {
                    path: 'mejor',
                    loadComponent: () =>
                      import('./pages/home-dev/reportes-dev/feedback/mejor/mejor')
                        .then(m => m.ReporteMejorCalificadosDevComponent)
                  },
                  {
                    path: 'peor',
                    loadComponent: () =>
                      import('./pages/home-dev/reportes-dev/feedback/peor/peor')
                        .then(m => m.ReportePeorCalificadosDevComponent)
                  }
                ]
              },
              {
                path: 'comentarios',
                loadComponent: () =>
                  import('./pages/home-dev/reportes-dev/comentarios/comentarios')
                    .then(m => m.ReporteComentariosDevComponent)
              },
              {
                path: 'top5',
                loadComponent: () =>
                  import('./pages/home-dev/reportes-dev//top-5/top-5')
                    .then(m => m.ReporteTop5DevComponent)
              }
            ]
          }
        ]
      },
      {
        path: 'biblioteca',
        loadComponent: () =>
          import('./pages/home/pantalla-biblioteca-comun/pantalla-biblioteca-comun')
            .then(m => m.PantallaBibliotecaComun)
      },
       {
        path: 'wallet',
        loadComponent: () =>
          import('./pages/home/estado-wallet/estado-wallet')
            .then(m => m.WalletComponent)
      },
      {
        path: 'grupos-familiares',
        loadComponent: () =>
          import('./pages/home/pantalla-grupos-familiares/pantalla-grupos-familiares')
            .then(m => m.GruposFamiliaresComponent)
      },
      {
        path: 'grupos-familiares/:idGrupo',
        loadComponent: () =>
          import('./pages/home/pantalla-integrantes/pantalla-integrantes')
            .then(m => m.IntegrantesGrupoComponent)
      },
      {
        path: 'perfil-usuario/:mail',
        loadComponent: () =>
          import('./pages/home/perfil-usuario/perfil-usuario')
            .then(m => m.PerfilUsuarioComponent)
      }, 
      {
        path: 'juego/:id',
        loadComponent: () =>
          import('./pages/home/juego-detalle/juego-detalle')
            .then(m => m.JuegoDetalleComponent)
      },
      {
        path: 'reportes',
        loadComponent: () =>
          import('./pages/home/opciones-reportes/opciones-reportes')
            .then(m => m.OpcionesReportesComponent),
        children: [
          {
            path: 'personales/mejoresCalificadosBiblioteca',
            loadComponent: () =>
              import('./pages/home/opciones-reportes/mejor-calificados-biblioteca/mejor-calificados-biblioteca')
                .then(m => m.MejoresCalificadosBibliotecaComponent)
          },
          {
            path: 'historial-gastos',
            loadComponent: () =>
              import('./pages/home/opciones-reportes/historial-gastos/historial-gastos')
                .then(m => m.HistorialGastosComponent)
          },
          {
            path: 'personales/calificacionesPersonales',
            loadComponent: () =>
              import('./pages/home/opciones-reportes/calificaciones-personales-biblioteca/calificaciones-personales-biblioteca')
                .then(m => m.CalificacionesPersonalesComponent)
          },
          {
            path: 'personales/clasificacionesFavoritas',
            loadComponent: () =>
              import('./pages/home/opciones-reportes/clasificaciones-favoritas/clasificaciones-favoritas')
                .then(m => m.ClasificacionesFavoritasComponent)
          },
          {
            path: 'personales/prestadosMasTiempo',
            loadComponent: () =>
              import('./pages/home/opciones-reportes/prestados-mas-tiempo/prestados-mas-tiempo')
                .then(m => m.PrestadosMasTiempoComponent)
          },
          {
            path: 'personales/prestadosMejorCalificados',
            loadComponent: () =>
              import('./pages/home/opciones-reportes/prestados-mejor-calificados/prestados-mejor-calificados')
                .then(m => m.PrestadosMejorCalificadosComponent)
          }
        ]
      },
      
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      }
    ]
  },
//redirecciones
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
