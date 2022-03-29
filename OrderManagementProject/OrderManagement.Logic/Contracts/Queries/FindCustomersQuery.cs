using OrderManagement.Dtos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Handlers.Queries
{
    public record FindCustomersQuery (Rating? rating = null) : IQuery<IEnumerable<CustomerDto>>
    {

    }
}
