import { Routes } from '@angular/router';
import { ProdutoListaComponent } from './pages/produtos/produto-lista.component';
import { ProdutoFormComponent } from './pages/produtos/produto-form.component';
import { PedidoListaComponent } from './pages/pedidos/pedido-lista.component';
import { PedidoFormComponent } from './pages/pedidos/pedido-form.component'

export const routes: Routes = [
  { path: '', redirectTo: 'produtos', pathMatch: 'full' },
  { path: 'produtos', component: ProdutoListaComponent },
  { path: 'produtos/novo', component: ProdutoFormComponent },
  { path: 'produtos/editar/:id', component: ProdutoFormComponent },
  { path: 'pedidos', component: PedidoListaComponent },
  { path: 'pedidos/novo', component: PedidoFormComponent },
  { path: '**', redirectTo: 'produtos' }
];